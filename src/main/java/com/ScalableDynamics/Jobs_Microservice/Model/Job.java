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
  private int jobId;

  private boolean publicView;

  private String Company;

  private String Title;

  private String Description;

  private double Salary;

  private String Location;

  private String Industry;
}
