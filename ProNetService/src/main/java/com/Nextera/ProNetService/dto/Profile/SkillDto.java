package com.Nextera.ProNetService.dto.Profile;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDto {

    private Integer skillId;
    private String name;
    private OffsetDateTime createdAt;
}
