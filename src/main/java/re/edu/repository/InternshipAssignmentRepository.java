package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import re.edu.entity.InternshipAssignment;

import java.util.List;
import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long>, JpaSpecificationExecutor<InternshipAssignment> {
    List<InternshipAssignment> findByStudentStudentId(Long studentId);
    List<InternshipAssignment> findByMentorMentorId(Long mentorId);
    List<InternshipAssignment> findByPhaseId(Long phaseId);
    Optional<InternshipAssignment> findByStudentStudentIdAndMentorMentorIdAndPhaseId(Long studentId, Long mentorId, Long phaseId);
    boolean existsByStudentStudentIdAndPhaseId(Long studentId, Long phaseId);
}
