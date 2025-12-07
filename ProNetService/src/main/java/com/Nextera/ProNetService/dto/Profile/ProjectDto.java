package com.Nextera.ProNetService.dto.Profile;


import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private Integer projectId;
    private Integer userId;

    private String title;
    private String description;
    private String url;
    private LocalDate startDate;
    private LocalDate endDate;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

