package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.AssessmentRound;

import java.util.List;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Long> {
    List<AssessmentRound> findByPhaseId(Long phaseId);
}
