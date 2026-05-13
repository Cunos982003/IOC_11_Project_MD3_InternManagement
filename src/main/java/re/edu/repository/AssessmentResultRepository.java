package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import re.edu.entity.AssessmentResult;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long>, JpaSpecificationExecutor<AssessmentResult> {
    boolean existsByAssignmentIdAndRoundIdAndCriterionId(Long assignmentId, Long roundId, Long criterionId);
}
