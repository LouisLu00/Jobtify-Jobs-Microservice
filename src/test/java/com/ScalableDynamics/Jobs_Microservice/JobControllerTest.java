package com.ScalableDynamics.Jobs_Microservice;

import com.ScalableDynamics.Jobs_Microservice.Controller.JobController;
import com.ScalableDynamics.Jobs_Microservice.Model.Job;
import com.ScalableDynamics.Jobs_Microservice.Service.JobService;
import com.ScalableDynamics.Jobs_Microservice.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
public class JobControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JobService jobService;

  @Test
  public void testGetAllJobs() throws Exception {
    Job job1 = new Job(1L, true, "Tech Corp", "Software Engineer", "Develops software", 120000, "New York", "IT", Job.Status.ACTIVE, 0, 36.2, 32.1, "");
    Job job2 = new Job(2L, true, "Product Corp", "Product Manager", "Manages products", 110000, "San Francisco", "IT", Job.Status.ACTIVE, 0, 36.2, 32.1, "");

    // Mock paginated response
    Pageable pageable = PageRequest.of(0, 10); // Page 0 with a size of 10
    PageImpl<Job> pagedJobs = new PageImpl<>(Arrays.asList(job1, job2), pageable, 2);

    when(jobService.getAllJobs(pageable)).thenReturn(pagedJobs);

    mockMvc.perform(get("/api/jobs").param("page", "0").param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("Software Engineer"))
            .andExpect(jsonPath("$.content[1].title").value("Product Manager"));
  }

  @Test
  public void testAddJob() throws Exception {
    // Create a Job instance with all required fields and the enum status
    Job job = new Job(1L, true, "Tech Corp", "Software Engineer", "Develops software", 120000, "New York", "IT", Job.Status.ACTIVE, 0, 36.2, 32.1, "");

    // Mock the service to return the job when called
    when(jobService.addJob(any(Job.class))).thenReturn(job);

    // Define JSON content for the request, excluding applicantCount and status to rely on defaults
    mockMvc.perform(post("/api/jobs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"publicView\": true, \"company\": \"Tech Corp\", \"title\": \"Software Engineer\", " +
                            "\"description\": \"Develops software\", \"salary\": 120000, \"location\": \"New York\", \"industry\": \"IT\" }"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.company").value("Tech Corp"))
            .andExpect(jsonPath("$.title").value("Software Engineer"))
            .andExpect(jsonPath("$.salary").value(120000))
            .andExpect(jsonPath("$.status").value("ACTIVE")) // Check for the enum status
            .andExpect(jsonPath("$.applicantCount").value(0)); // Check for default applicantCount
  }

  @Test
  public void testGetJobByIdNotFound() throws Exception {
    when(jobService.getJobById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/jobs/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateJob() throws Exception {
    Job updatedJob = new Job(1L, true, "Updated Corp", "Senior Engineer", "Leads development", 150000, "San Francisco", "IT", Job.Status.ACTIVE, 0, 36.2, 32.1, "");
    when(jobService.updateJob(Mockito.eq(1L), any(Job.class))).thenReturn(Optional.of(updatedJob));

    mockMvc.perform(put("/api/jobs/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"publicView\": true, \"company\": \"Updated Corp\", \"title\": \"Senior Engineer\", " +
                            "\"description\": \"Leads development\", \"salary\": 150000, \"location\": \"San Francisco\", \"industry\": \"IT\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.company").value("Updated Corp"))
            .andExpect(jsonPath("$.salary").value(150000));
  }
}
