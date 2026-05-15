package re.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.edu.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.user WHERE s.studentId IN " +
           "(SELECT ia.student.studentId FROM InternshipAssignment ia WHERE ia.mentor.mentorId = :mentorId)")
    Page<Student> findAllByMentorId(@Param("mentorId") Long mentorId, Pageable pageable);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.user WHERE s.studentId = :id")
    Optional<Student> findByIdWithUser(@Param("id") Long id);
}
