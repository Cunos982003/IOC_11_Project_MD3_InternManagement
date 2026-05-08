package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.InternshipAssignment;

import java.util.List;
import java.util.Optional;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Long> {
    List<InternshipAssignment> findByStudentId(Long studentId);
    List<InternshipAssignment> findByMentorId(Long mentorId);
    List<InternshipAssignment> findByPhaseId(Long phaseId);
    Optional<InternshipAssignment> findByStudentIdAndMentorIdAndPhaseId(Long studentId, Long mentorId, Long phaseId);
    boolean existsByStudentIdAndPhaseId(Long studentId, Long phaseId);
}
