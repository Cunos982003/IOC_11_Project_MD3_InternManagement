package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import re.edu.entity.InternshipAssignment;

import java.util.List;
import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long>, JpaSpecificationExecutor<InternshipAssignment> {
    boolean existsByStudentStudentIdAndPhaseId(Long studentId, Long phaseId);
}
