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
public class Jobs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int jobId;

  private boolean publicView;

  private String company;

  private String jobTitle;

  private String description;

  private double minSalary;

  private double maxSalary;

  private String officeLocation;
}
