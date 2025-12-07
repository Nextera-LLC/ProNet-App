package com.Nextera.ProNetService.dto.Profile;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDto {

    private Integer educationId;
    private Integer userId;

    private String institution;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String grade;
    private String description;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

