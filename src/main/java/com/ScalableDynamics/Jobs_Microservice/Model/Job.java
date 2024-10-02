package com.ScalableDynamics.Jobs_Microservice.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long jobId;

  private boolean publicView;

  private String company;

  private String title;

  private String description;

  private double salary;

  private String location;

  private String industry;
}
