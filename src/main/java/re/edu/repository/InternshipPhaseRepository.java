package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.InternshipPhase;

import java.util.Optional;

public interface InternshipPhaseRepository extends JpaRepository<InternshipPhase, Long> {
    boolean existsByName(String name);

    @Query("SELECT ip FROM InternshipPhase ip WHERE ip.id = :id")
    Optional<InternshipPhase> findByIdWithDetails(@Param("id") Long id);
}
