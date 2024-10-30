package com.ScalableDynamics.Jobs_Microservice.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.ScalableDynamics.Jobs_Microservice.Model.Job;
import com.ScalableDynamics.Jobs_Microservice.Service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Job management API")
public class JobController {

  @Autowired
  private JobService jobService;

  @Operation(summary = "Get all jobs", description = "Retrieve a paginated list of all available job postings")
  @GetMapping
  public ResponseEntity<?> getAllJobs(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Job> jobs = jobService.getAllJobs(pageable);
    return ResponseEntity.ok(jobs);
  }

  @Operation(summary = "Get a job by ID", description = "Retrieve details of a job by its ID")
  @GetMapping("/{id}")
  public ResponseEntity<Job> getJobById(@PathVariable Long id) {
    Optional<Job> job = jobService.getJobById(id);
    return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Create a new job", description = "Post a new job listing")
  @PostMapping
  public ResponseEntity<Job> addJob(@RequestBody Job job) {
    Job newJob = jobService.addJob(job);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newJob.getJobId())
            .toUri();
    return ResponseEntity.created(location).body(newJob);
  }

  @Operation(summary = "Update a job", description = "Update an existing job listing by ID")
  @PutMapping("/{id}")
  public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
    Optional<Job> updatedJob = jobService.updateJob(id, jobDetails);
    return updatedJob.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete a job", description = "Delete an existing job by its ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
    jobService.deleteJob(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Search jobs", description = "Search for jobs by title or location")
  @GetMapping("/search")
  public List<Job> searchJobs(@RequestParam String title, @RequestParam String location) {
    return jobService.searchJobs(title, location);
  }
}
