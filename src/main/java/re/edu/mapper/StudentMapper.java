package re.edu.mapper;

import org.springframework.stereotype.Component;
import re.edu.dto.response.StudentResponse;
import re.edu.entity.Student;

@Component
public class StudentMapper {

    public StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .userId(student.getUser().getId())
                .username(student.getUser().getUsername())
                .email(student.getUser().getEmail())
                .studentCode(student.getStudentCode())
                .fullName(student.getFullName())
                .dateOfBirth(student.getDateOfBirth())
                .phone(student.getPhone())
                .address(student.getAddress())
                .gpa(student.getGpa())
                .major(student.getMajor())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
