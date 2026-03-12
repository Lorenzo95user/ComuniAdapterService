package it.newtech.timetracker.task.entity;

import it.newtech.timetracker.project.entity.SubProject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name="start_time")
    private LocalTime startTime;
    
    @Column(name="end_time")
    private LocalTime endTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "billing_type", nullable = false)
    private BillingType billingType;

    
    @Column(precision = 10, scale = 2)
    private BigDecimal defaultRate;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_project_id", nullable = false)
    @JsonBackReference
    private SubProject subProject;

}

