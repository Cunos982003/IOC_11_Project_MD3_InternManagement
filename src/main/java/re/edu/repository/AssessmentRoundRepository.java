package re.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.AssessmentRound;
import re.edu.util.RoundStatus;

import java.util.List;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Long> {
    List<AssessmentRound> findByPhaseId(Long phaseId);
    Page<AssessmentRound> findByPhaseId(Long phaseId, Pageable pageable);
    Page<AssessmentRound> findByStatus(RoundStatus status, Pageable pageable);
    boolean existsByName(String name);
    List<AssessmentRound> findByPhaseIdAndStatus(Long phaseId, RoundStatus status);
}
