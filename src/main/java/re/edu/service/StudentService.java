package re.edu.service;

import re.edu.dto.request.StudentRequest;
import re.edu.dto.request.UpdateStudentRequest;
import re.edu.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents(String currentUsername);
    StudentResponse getStudentById(Long id, String currentUsername);
    StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, UpdateStudentRequest request, String currentUsername);
}
