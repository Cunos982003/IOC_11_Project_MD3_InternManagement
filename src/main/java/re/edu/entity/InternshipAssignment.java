package re.edu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.edu.util.AssignmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "internship_assignments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "phase_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private InternshipPhase phase;

    @CreationTimestamp
    @Column(name = "assigned_date", updatable = false)
    private LocalDateTime assignedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private AssignmentStatus status = AssignmentStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
