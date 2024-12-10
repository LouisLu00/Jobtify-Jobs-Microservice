package com.ScalableDynamics.Jobs_Microservice.Service;

import com.ScalableDynamics.Jobs_Microservice.Model.Job;
import com.ScalableDynamics.Jobs_Microservice.Repository.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  @Value("${server.base-url}")
  private String baseUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public Page<Job> getAllJobs(Pageable pageable) {
    return jobRepository.findAll(pageable);
  }
  // Get job by ID
  public Optional<Job> getJobById(Long id) {
    return jobRepository.findById(id);
  }

  // Add a new job
  public Job addJob(Job job) {
    return jobRepository.save(job);
  }

  // Add a new job asynchronously
  @Async
  @Transactional
  public void updateJobApplicantCountAsync(Job job, String callbackUrl) {
    // Save initial job data
    job.setStatus(Job.Status.PENDING);
    Job savedJob = jobRepository.save(job);

    savedJob.setApplicantCount(savedJob.getApplicantCount() + 1);
    savedJob.setStatus(Job.Status.ACTIVE);
    jobRepository.save(savedJob);

    if (callbackUrl != null && !callbackUrl.isEmpty()) {
      if (!callbackUrl.startsWith("http")) {
        callbackUrl = baseUrl + callbackUrl;
      }
      try {
        restTemplate.postForLocation(callbackUrl, savedJob);
      } catch (Exception e) {
        System.err.println("Failed to call callback URL: " + e.getMessage());
      }
    }
  }

  // Update an existing job
  public Optional<Job> updateJob(Long id, Job jobDetails) {
    return jobRepository.findById(id).map(job -> {
      if (jobDetails.getTitle() != null) {
        job.setTitle(jobDetails.getTitle());
      }
      if (jobDetails.getDescription() != null) {
        job.setDescription(jobDetails.getDescription());
      }
      if (jobDetails.getLocation() != null) {
        job.setLocation(jobDetails.getLocation());
      }
      if (jobDetails.getCompany() != null) {
        job.setCompany(jobDetails.getCompany());
      }
      if (jobDetails.getSalary() > 0) { // Assuming salary should be positive
        job.setSalary(jobDetails.getSalary());
      }
      if (jobDetails.getLongitude() != null) {
        job.setLongitude(jobDetails.getLongitude());
      }
      if (jobDetails.getLatitude() != null) {
        job.setLatitude(jobDetails.getLatitude());
      }
      if (jobDetails.getJobLink() != null) {
        job.setJobLink(jobDetails.getJobLink());
      }
      return jobRepository.save(job);
    });
  }


  // Delete a job
  public boolean deleteJob(Long id) {
    Optional<Job> job = jobRepository.findById(id);
    if (job.isPresent()) {
      jobRepository.delete(job.get());
      return true;
    }
    return false;
  }

  // Search jobs by title or location
  public List<Job> searchJobs(String title, String location) {
    return jobRepository.findByTitleContainingOrLocationContaining(title, location);
  }
}
