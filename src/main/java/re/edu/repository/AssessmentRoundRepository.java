package re.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.AssessmentRound;

import java.util.List;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Long> {
    Page<AssessmentRound> findByPhaseId(Long phaseId, Pageable pageable);
    boolean existsByName(String name);
}
