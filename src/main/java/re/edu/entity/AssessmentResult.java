package re.edu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_results",
       uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id", "round_id", "criterion_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private InternshipAssignment assignment;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private AssessmentRound round;

    @ManyToOne
    @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriteria criterion;

    @Column(nullable = false)
    private Double score;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "evaluated_by", nullable = false)
    private User evaluatedBy;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
