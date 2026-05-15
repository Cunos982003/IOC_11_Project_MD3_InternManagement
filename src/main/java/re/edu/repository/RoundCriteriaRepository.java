package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.RoundCriteria;

import java.util.List;
import java.util.Optional;

public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Long> {
    List<RoundCriteria> findByRoundId(Long roundId);
    boolean existsByRoundIdAndCriteriaId(Long roundId, Long criteriaId);
    void deleteByRoundId(Long roundId);

    @Query("SELECT rc FROM RoundCriteria rc " +
           "LEFT JOIN FETCH rc.round " +
           "LEFT JOIN FETCH rc.criteria " +
           "WHERE rc.id = :id")
    Optional<RoundCriteria> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT rc FROM RoundCriteria rc " +
           "LEFT JOIN FETCH rc.round " +
           "LEFT JOIN FETCH rc.criteria " +
           "WHERE rc.round.id = :roundId")
    List<RoundCriteria> findByRoundIdWithDetails(@Param("roundId") Long roundId);
}
