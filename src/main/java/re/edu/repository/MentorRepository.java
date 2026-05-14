package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.edu.entity.Mentor;

import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
