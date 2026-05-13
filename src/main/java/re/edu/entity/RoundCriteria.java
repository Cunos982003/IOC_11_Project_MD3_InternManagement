package re.edu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "round_criteria", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"round_id", "criterion_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private AssessmentRound round;

    @ManyToOne
    @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriteria criteria;

    @Column(nullable = false)
    private Double weight;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
