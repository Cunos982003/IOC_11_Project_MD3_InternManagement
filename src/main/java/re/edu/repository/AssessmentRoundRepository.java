package re.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.AssessmentRound;

import java.util.Optional;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Long> {
    Page<AssessmentRound> findByPhaseId(Long phaseId, Pageable pageable);
    boolean existsByName(String name);

    @Query("SELECT ar FROM AssessmentRound ar LEFT JOIN FETCH ar.phase WHERE ar.id = :id")
    Optional<AssessmentRound> findByIdWithPhase(@Param("id") Long id);
}
