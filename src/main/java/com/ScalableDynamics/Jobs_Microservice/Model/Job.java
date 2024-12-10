package com.ScalableDynamics.Jobs_Microservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Job")
public class Job implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long jobId;

  @NotNull
  @Column(nullable = false)
  private boolean publicView = false; // Default value: false

  @NotNull
  @Column(nullable = false)
  private String company;

  @NotNull
  @Column(nullable = false)
  private String title;

  @Column(length = 500)
  private String description;

  @PositiveOrZero
  @Column(nullable = false)
  private double salary = 0.0; // Default to 0 if not provided

  private String location;

  private String industry;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status = Status.PENDING; // Default value: PENDING

  @PositiveOrZero
  @Column(nullable = false)
  private int applicantCount = 0; // Default value: 0

  @Column(nullable = true)
  private Double latitude;

  @Column(nullable = true)
  private Double longitude;

  public enum Status {
    PENDING,
    ACTIVE,
    CLOSED,
    FILLED
  }
}
