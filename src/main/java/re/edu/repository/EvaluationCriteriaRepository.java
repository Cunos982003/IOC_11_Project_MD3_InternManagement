package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.EvaluationCriteria;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Long> {
    boolean existsByName(String name);
}
