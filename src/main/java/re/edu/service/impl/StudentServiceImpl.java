package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.edu.dto.request.StudentRequest;
import re.edu.dto.request.UpdateStudentRequest;
import re.edu.dto.response.StudentResponse;
import re.edu.entity.Mentor;
import re.edu.entity.Student;
import re.edu.entity.User;
import re.edu.exception.ConflictException;
import re.edu.exception.ForbiddenException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.StudentMapper;
import re.edu.repository.MentorRepository;
import re.edu.repository.StudentRepository;
import re.edu.repository.UserRepository;
import re.edu.service.StudentService;
import re.edu.util.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponse> getAllStudents(String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);

        if (currentUser.getRole() == Role.ADMIN) {
            return studentRepository.findAll().stream().map(studentMapper::toResponse).toList();
        }

        // MENTOR: chỉ xem sinh viên được phân công
        Mentor mentor = mentorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin mentor"));
        return studentRepository.findAllByMentorId(mentor.getId()).stream()
                .map(studentMapper::toResponse).toList();
    }

    @Override
    public StudentResponse getStudentById(Long id, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Student student = findById(id);

        if (currentUser.getRole() == Role.STUDENT) {
            Student ownStudent = studentRepository.findByUserId(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ sinh viên"));
            if (!ownStudent.getId().equals(id)) {
                throw new ForbiddenException("Bạn chỉ có thể xem thông tin của mình");
            }
        }
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại với ID: " + request.getUserId()));

        if (user.getRole() != Role.STUDENT) {
            throw new ConflictException("Người dùng phải có vai trò STUDENT");
        }
        if (studentRepository.existsByUserId(request.getUserId())) {
            throw new ConflictException("Người dùng này đã có hồ sơ sinh viên");
        }
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new ConflictException("Mã sinh viên đã tồn tại");
        }

        Student student = Student.builder()
                .user(user)
                .studentCode(request.getStudentCode())
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .address(request.getAddress())
                .gpa(request.getGpa())
                .major(request.getMajor())
                .build();
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse updateStudent(Long id, UpdateStudentRequest request, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Student student = findById(id);

        if (currentUser.getRole() == Role.STUDENT) {
            Student ownStudent = studentRepository.findByUserId(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ sinh viên"));
            if (!ownStudent.getId().equals(id)) {
                throw new ForbiddenException("Bạn chỉ có thể cập nhật thông tin của mình");
            }
        }

        if (request.getFullName() != null) student.setFullName(request.getFullName());
        if (request.getDateOfBirth() != null) student.setDateOfBirth(request.getDateOfBirth());
        if (request.getPhone() != null) student.setPhone(request.getPhone());
        if (request.getAddress() != null) student.setAddress(request.getAddress());
        if (request.getGpa() != null) student.setGpa(request.getGpa());
        if (request.getMajor() != null) student.setMajor(request.getMajor());

        return studentMapper.toResponse(studentRepository.save(student));
    }

    private Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên không tồn tại với ID: " + id));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }
}
