package com.Nextera.ProNetService.Model.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "user_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_user_skill",
                        columnNames = {"user_id", "name"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_skill_id")
    private Integer userSkillId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name; // skill name, e.g. "Angular"

    @Column(name = "level", length = 50)
    private String level; // Beginner / Intermediate / Advanced

    @Column(name = "years_experience", precision = 4, scale = 2)
    private BigDecimal yearsExperience;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}

