package com.ScalableDynamics.Jobs_Microservice.Controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(
          summary = "Get all jobs",
          description = "Retrieve a paginated list of all available job postings"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Successfully retrieved list of jobs",
                  content = @Content(schema = @Schema(implementation = Page.class))
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid pagination parameters",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @GetMapping
  public ResponseEntity<?> getAllJobs(
          @Parameter(description = "Page number, starting from 0", example = "0")
          @RequestParam(defaultValue = "0") int page,
          @Parameter(description = "Number of records per page", example = "10")
          @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Job> jobs = jobService.getAllJobs(pageable);
    return ResponseEntity.ok(jobs);
  }

  @Operation(
          summary = "Get a job by ID",
          description = "Retrieve details of a job by its ID"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Job found and returned successfully",
                  content = @Content(schema = @Schema(implementation = Job.class))
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Job not found",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @GetMapping("/{id}")
  public ResponseEntity<Job> getJobById(
          @Parameter(description = "ID of the job to retrieve", example = "1")
          @PathVariable Long id) {
    Optional<Job> job = jobService.getJobById(id);
    return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
          summary = "Create a new job",
          description = "Post a new job listing"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "201",
                  description = "Job created successfully",
                  content = @Content(schema = @Schema(implementation = Job.class))
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid job data",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @PostMapping
  public ResponseEntity<Job> addJob(
          @Parameter(description = "Job object containing job details", required = true)
          @RequestBody Job job) {
    Job newJob = jobService.addJob(job);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newJob.getJobId())
            .toUri();
    return ResponseEntity.created(location).body(newJob);
  }

  @Operation(
          summary = "Update a job",
          description = "Update an existing job listing by ID"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Job updated successfully",
                  content = @Content(schema = @Schema(implementation = Job.class))
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Job not found",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid job data",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @PutMapping("/{id}")
  public ResponseEntity<Job> updateJob(
          @Parameter(description = "ID of the job to update", example = "1", required = true)
          @PathVariable Long id,
          @Parameter(description = "Updated job details", required = true)
          @RequestBody Job jobDetails) {
    Optional<Job> updatedJob = jobService.updateJob(id, jobDetails);
    return updatedJob.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
          summary = "Delete a job",
          description = "Delete an existing job by its ID"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "204",
                  description = "Job deleted successfully"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Job not found"
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error"
          )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteJob(
          @Parameter(description = "ID of the job to be deleted", example = "1")
          @PathVariable Long id) {

    boolean isDeleted = jobService.deleteJob(id);
    return isDeleted ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
  }

  @Operation(
          summary = "Search jobs",
          description = "Search for jobs by title, location, or both. Returns all jobs if no parameters are provided."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Successfully retrieved list of jobs",
                  content = @Content(schema = @Schema(implementation = Job.class))
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid search parameters",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @GetMapping("/search")
  public ResponseEntity<List<Job>> searchJobs(
          @Parameter(description = "Title of the job to search for", example = "Software Engineer")
          @RequestParam(required = false) String title,
          @Parameter(description = "Location of the job to search for", example = "New York")
          @RequestParam(required = false) String location) {

    List<Job> jobs = jobService.searchJobs(title, location);
    return ResponseEntity.ok(jobs);
  }


  @Operation(
          summary = "Asynchronously update job applicant count (increase by 1)",
          description = "Increment the applicant count for a specified job by 1 asynchronously. Returns a 202 Accepted response with a URI to check the job's status."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "202",
                  description = "Job applicant count update accepted for asynchronous processing",
                  content = @Content,
                  headers = @Header(
                          name = "Location",
                          description = "URI to check the job's status",
                          schema = @Schema(type = "string")
                  )
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Job not found",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @PostMapping("/async/update/{jobId}")
  public ResponseEntity<Void> addJobAsync(
          @Parameter(description = "ID of the job to be updated", example = "1", required = true)
          @PathVariable Long jobId) {

    Optional<Job> optionalJob = jobService.getJobById(jobId);
    if (!optionalJob.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Job job = optionalJob.get();
    jobService.updateJobApplicantCountAsync(job, "/api/jobs/default-job-completion-callback");

    // Build URI for checking job status
    URI statusUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/jobs/status/{id}")
            .buildAndExpand(jobId)
            .toUri();

    return ResponseEntity.accepted().location(statusUri).build();
  }

  @Operation(
          summary = "Get job status by ID",
          description = "Retrieve the current status of a job by its ID"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Job status retrieved successfully",
                  content = @Content(schema = @Schema(implementation = String.class))
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Job not found",
                  content = @Content
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Server error",
                  content = @Content
          )
  })
  @GetMapping("/status/{id}")
  public ResponseEntity<?> getJobStatus(@PathVariable Long id) {
    Optional<Job> job = jobService.getJobById(id);
    return job.map(j -> ResponseEntity.ok(j.getStatus()))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
          summary = "Handle job completion callback",
          description = "This endpoint is used as a callback URL to receive job completion details. It logs job details and returns a 201 Created response with the job details and a link to access the job."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Job processed successfully",
                  content = @Content(schema = @Schema(implementation = Job.class))),
          @ApiResponse(responseCode = "400", description = "Invalid job data",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Server error",
                  content = @Content)
  })
  @PostMapping("/default-job-completion-callback")
  public ResponseEntity<Job> handleDefaultJobCompletion(@RequestBody Job job) {
    System.out.println("Default callback received for job ID: " + job.getJobId());
    System.out.println("Job status: " + job.getStatus());

    URI jobUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/jobs/{id}")
            .buildAndExpand(job.getJobId())
            .toUri();

    return ResponseEntity.created(jobUri).body(job);
  }
}
