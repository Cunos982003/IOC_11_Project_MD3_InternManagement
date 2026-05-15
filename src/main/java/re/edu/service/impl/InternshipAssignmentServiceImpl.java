package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.InternshipAssignmentRequest;
import re.edu.dto.request.UpdateAssignmentStatusRequest;
import re.edu.dto.response.InternshipAssignmentResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.InternshipAssignment;
import re.edu.entity.InternshipPhase;
import re.edu.entity.Mentor;
import re.edu.entity.Student;
import re.edu.entity.User;
import re.edu.exception.ConflictException;
import re.edu.exception.ForbiddenException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.InternshipAssignmentMapper;
import re.edu.repository.InternshipAssignmentRepository;
import re.edu.repository.InternshipPhaseRepository;
import re.edu.repository.MentorRepository;
import re.edu.repository.StudentRepository;
import re.edu.repository.UserRepository;
import re.edu.service.InternshipAssignmentService;
import re.edu.util.AssignmentStatus;
import re.edu.util.Role;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipAssignmentServiceImpl implements InternshipAssignmentService {

    private final InternshipAssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
    private final InternshipPhaseRepository phaseRepository;
    private final UserRepository userRepository;
    private final InternshipAssignmentMapper assignmentMapper;

    @Override
    public PaginatedData<InternshipAssignmentResponse> getAllAssignments(
            String currentUsername, int page, int pageSize, Long studentId, Long mentorId, Long phaseId) {

        User currentUser = findUserByUsername(currentUsername);
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Specification<InternshipAssignment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Role-based filtering
            if (currentUser.getRole() == Role.MENTOR) {
                predicates.add(cb.equal(root.get("mentor").get("mentorId"), currentUser.getId()));
            } else if (currentUser.getRole() == Role.STUDENT) {
                predicates.add(cb.equal(root.get("student").get("studentId"), currentUser.getId()));
            }

            // Additional filters
            if (studentId != null) {
                predicates.add(cb.equal(root.get("student").get("studentId"), studentId));
            }

            if (mentorId != null) {
                predicates.add(cb.equal(root.get("mentor").get("mentorId"), mentorId));
            }

            if (phaseId != null) {
                predicates.add(cb.equal(root.get("phase").get("id"), phaseId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<InternshipAssignment> assignments = assignmentRepository.findAll(spec, pageable);

        List<InternshipAssignmentResponse> items = assignments.getContent().stream()
                .map(assignmentMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(assignments.getTotalPages())
                .totalItems(assignments.getTotalElements())
                .build();

        return PaginatedData.<InternshipAssignmentResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public InternshipAssignmentResponse getAssignmentById(Long id, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        InternshipAssignment assignment = assignmentRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phân công với ID: " + id));

        // Role-based access control
        if (currentUser.getRole() == Role.MENTOR) {
            if (!assignment.getMentor().getMentorId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể xem phân công của mình");
            }
        } else if (currentUser.getRole() == Role.STUDENT) {
            if (!assignment.getStudent().getStudentId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể xem phân công của mình");
            }
        }

        return assignmentMapper.toResponse(assignment);
    }

    @Override
    @Transactional
    public InternshipAssignmentResponse createAssignment(InternshipAssignmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên với ID: " + request.getStudentId()));

        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên với ID: " + request.getMentorId()));

        InternshipPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giai đoạn với ID: " + request.getPhaseId()));

        if (assignmentRepository.existsByStudentStudentIdAndPhaseId(request.getStudentId(), request.getPhaseId())) {
            throw new ConflictException("Sinh viên đã được phân công trong giai đoạn này");
        }

        InternshipAssignment assignment = InternshipAssignment.builder()
                .student(student)
                .mentor(mentor)
                .phase(phase)
                .status(AssignmentStatus.PENDING)
                .build();

        InternshipAssignment saved = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public InternshipAssignmentResponse updateAssignmentStatus(Long id, UpdateAssignmentStatusRequest request) {
        InternshipAssignment assignment = findById(id);
        assignment.setStatus(request.getStatus());
        InternshipAssignment updated = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        InternshipAssignment assignment = findById(id);
        if (AssignmentStatus.IN_PROGRESS.equals(assignment.getStatus())) {
            throw new ConflictException("Không thể xóa phân công đang diễn ra");
        }

        assignmentRepository.delete(assignment);
    }

    private InternshipAssignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phân công với ID: " + id));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }
}
