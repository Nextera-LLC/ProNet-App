package com.Nextera.ProNetService.dto.Profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDto{

    private Integer experienceId;
    private Integer userId;

    private String companyName;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    @JsonProperty("isCurrent")
    private boolean isCurrent;
    private String description;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
