package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.EvaluationCriteria;

import java.util.Optional;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Long> {
    boolean existsByName(String name);

    @Query("SELECT ec FROM EvaluationCriteria ec WHERE ec.id = :id")
    Optional<EvaluationCriteria> findByIdWithDetails(@Param("id") Long id);
}
