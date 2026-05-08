package re.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);
    boolean existsByStudentCode(String studentCode);
    boolean existsByUserId(Long userId);

    @Query("SELECT s FROM Student s JOIN InternshipAssignment ia ON ia.student = s WHERE ia.mentor.id = :mentorId")
    List<Student> findAllByMentorId(@Param("mentorId") Long mentorId);
}
