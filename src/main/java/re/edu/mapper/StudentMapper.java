package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.response.StudentResponse;
import re.edu.entity.Student;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ModelMapper modelMapper;

    public StudentResponse toResponse(Student student) {
        StudentResponse response = modelMapper.map(student, StudentResponse.class);
        if (student.getUser() != null) {
            response.setUserId(student.getUser().getId());
            response.setUsername(student.getUser().getUsername());
            response.setEmail(student.getUser().getEmail());
        }
        return response;
    }
}
