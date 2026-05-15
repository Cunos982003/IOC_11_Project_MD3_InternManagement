package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.Mentor;

import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
    @Query("SELECT m FROM Mentor m LEFT JOIN FETCH m.user WHERE m.mentorId = :id")
    Optional<Mentor> findByIdWithUser(@Param("id") Long id);
}
