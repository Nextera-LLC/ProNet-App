package com.Nextera.ProNetService.dto.Profile;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkillDto {

    private Integer userSkillId;
    private Integer userId;

    private String name;
    private String level;
    private BigDecimal yearsExperience;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

