package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.AssessmentResult;

import java.util.Optional;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long>, JpaSpecificationExecutor<AssessmentResult> {
    boolean existsByAssignmentIdAndRoundIdAndCriterionId(Long assignmentId, Long roundId, Long criterionId);

    @Query("SELECT ar FROM AssessmentResult ar " +
           "LEFT JOIN FETCH ar.assignment a " +
           "LEFT JOIN FETCH a.student s " +
           "LEFT JOIN FETCH s.user " +
           "LEFT JOIN FETCH a.mentor m " +
           "LEFT JOIN FETCH m.user " +
           "LEFT JOIN FETCH a.phase " +
           "LEFT JOIN FETCH ar.round " +
           "LEFT JOIN FETCH ar.criterion " +
           "WHERE ar.id = :id")
    Optional<AssessmentResult> findByIdWithDetails(@Param("id") Long id);
}
