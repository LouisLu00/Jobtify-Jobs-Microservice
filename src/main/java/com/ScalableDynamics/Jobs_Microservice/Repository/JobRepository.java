package com.ScalableDynamics.Jobs_Microservice.Repository;

import com.ScalableDynamics.Jobs_Microservice.Model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
  // Custom query to find jobs by title or location
  List<Job> findByTitleContainingOrLocationContaining(String title, String location);
}
