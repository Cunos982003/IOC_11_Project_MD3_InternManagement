package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.RoundCriteria;

import java.util.List;

public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Long> {
    List<RoundCriteria> findByRoundId(Long roundId);
    boolean existsByRoundIdAndCriteriaId(Long roundId, Long criteriaId);
    void deleteByRoundId(Long roundId);
    boolean existsByCriteriaId(Long criteriaId);
}
