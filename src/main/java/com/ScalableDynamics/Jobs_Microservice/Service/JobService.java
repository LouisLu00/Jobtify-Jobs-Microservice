package com.ScalableDynamics.Jobs_Microservice.Service;

import com.ScalableDynamics.Jobs_Microservice.Model.Job;
import com.ScalableDynamics.Jobs_Microservice.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
  @Autowired
  private JobRepository jobRepository;

  public List<Job> getAllJobs() {
    return jobRepository.findAll();
  }
  // Get job by ID
  public Optional<Job> getJobById(Long id) {
    return jobRepository.findById(id);
  }

  // Add a new job
  public Job addJob(Job job) {
    return jobRepository.save(job);
  }

  // Update an existing job
  public Optional<Job> updateJob(Long id, Job jobDetails) {
    return jobRepository.findById(id).map(job -> {
      job.setTitle(jobDetails.getTitle());
      job.setDescription(jobDetails.getDescription());
      job.setLocation(jobDetails.getLocation());
      job.setCompany(jobDetails.getCompany());
      job.setSalary(jobDetails.getSalary());
      return jobRepository.save(job);
    });
  }

  // Delete a job
  public void deleteJob(Long id) {
    jobRepository.deleteById(id);
  }

  // Search jobs by title or location
  public List<Job> searchJobs(String title, String location) {
    return jobRepository.findByTitleContainingOrLocationContaining(title, location);
  }



}
