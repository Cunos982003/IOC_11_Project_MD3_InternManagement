package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.StudentRequest;
import re.edu.dto.request.UpdateStudentRequest;
import re.edu.dto.response.PaginatedData;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginatedData<StudentResponse> getAllStudents(String currentUsername, int page, int pageSize) {
        User currentUser = findUserByUsername(currentUsername);
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Student> students;
        if (currentUser.getRole() == Role.ADMIN) {
            students = studentRepository.findAll(pageable);
        } else {
            Mentor mentor = mentorRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin mentor"));
            students = studentRepository.findAllByMentorId(mentor.getMentorId(), pageable);
        }

        List<StudentResponse> items = students.getContent().stream()
                .map(studentMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(students.getTotalPages())
                .totalItems(students.getTotalElements())
                .build();

        return PaginatedData.<StudentResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public StudentResponse getStudentById(Long id, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Student student = studentRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên không tồn tại với ID: " + id));

        if (student.getUser().getRole() != Role.STUDENT) {
            throw new ResourceNotFoundException("Sinh viên không tồn tại với ID: " + id);
        }

        if (currentUser.getRole() == Role.STUDENT) {
            if (!student.getStudentId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể xem thông tin của mình");
            }
        }
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }

        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new ConflictException("Mã sinh viên đã tồn tại");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhone())
                .role(Role.STUDENT)
                .isActive(true)
                .build();
        User savedUser = userRepository.save(user);

        Student student = Student.builder()
                .user(savedUser)
                .studentCode(request.getStudentCode())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .major(request.getMajor())
                .className(request.getClassName())
                .build();

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse updateStudent(Long id, UpdateStudentRequest request, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Student student = studentRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên không tồn tại với ID: " + id));

        if (currentUser.getRole() == Role.STUDENT) {
            if (!student.getStudentId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể cập nhật thông tin của mình");
            }
        }

        if (request.getDateOfBirth() != null) student.setDateOfBirth(request.getDateOfBirth());
        if (request.getAddress() != null) student.setAddress(request.getAddress());
        if (request.getMajor() != null) student.setMajor(request.getMajor());

        return studentMapper.toResponse(studentRepository.save(student));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }
}
