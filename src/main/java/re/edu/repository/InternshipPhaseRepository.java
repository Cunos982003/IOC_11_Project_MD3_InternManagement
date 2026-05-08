package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.InternshipPhase;

public interface InternshipPhaseRepository extends JpaRepository<InternshipPhase, Long> {
    boolean existsByName(String name);
}
