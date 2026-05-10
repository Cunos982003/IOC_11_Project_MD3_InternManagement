package re.edu.service;

import re.edu.dto.request.StudentRequest;
import re.edu.dto.request.UpdateStudentRequest;
import re.edu.dto.response.PaginatedData;
import re.edu.dto.response.StudentResponse;

public interface StudentService {
    PaginatedData<StudentResponse> getAllStudents(String currentUsername, int page, int pageSize);
    StudentResponse getStudentById(Long id, String currentUsername);
    StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, UpdateStudentRequest request, String currentUsername);
}
