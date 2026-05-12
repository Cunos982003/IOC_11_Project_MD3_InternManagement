package re.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.EvaluationCriteria;
import re.edu.util.CriteriaType;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Long> {
    boolean existsByName(String name);
    Page<EvaluationCriteria> findByIsActiveTrue(Pageable pageable);
    Page<EvaluationCriteria> findByIsActiveTrueAndCriteriaType(CriteriaType criteriaType, Pageable pageable);
}
