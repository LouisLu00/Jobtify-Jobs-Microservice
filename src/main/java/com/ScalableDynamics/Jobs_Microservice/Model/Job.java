package com.ScalableDynamics.Jobs_Microservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Job")
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
