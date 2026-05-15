package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.InternshipAssignment;

import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long>, JpaSpecificationExecutor<InternshipAssignment> {
    boolean existsByStudentStudentIdAndPhaseId(Long studentId, Long phaseId);

    @Query("SELECT ia FROM InternshipAssignment ia " +
           "LEFT JOIN FETCH ia.student s " +
           "LEFT JOIN FETCH s.user " +
           "LEFT JOIN FETCH ia.mentor m " +
           "LEFT JOIN FETCH m.user " +
           "LEFT JOIN FETCH ia.phase " +
           "WHERE ia.id = :id")
    Optional<InternshipAssignment> findByIdWithDetails(@Param("id") Long id);
}
