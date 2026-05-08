package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.AssessmentResult;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    List<AssessmentResult> findByAssignmentId(Long assignmentId);
    List<AssessmentResult> findByMentorId(Long mentorId);
    boolean existsByAssignmentIdAndRoundCriteriaId(Long assignmentId, Long roundCriteriaId);
}
