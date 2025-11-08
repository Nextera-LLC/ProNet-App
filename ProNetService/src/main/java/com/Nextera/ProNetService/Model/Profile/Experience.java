package com.Nextera.ProNetService.Model.Profile;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
 import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "experience")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Integer experienceId;

    // FK -> users(user_id); keep as scalar for simplicity
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate; // nullable if isCurrent = true

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "is_current", nullable = false)
    private boolean isCurrent;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
