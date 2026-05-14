package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.MentorRequest;
import re.edu.dto.request.UpdateMentorRequest;
import re.edu.dto.response.MentorResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.Mentor;
import re.edu.entity.User;
import re.edu.exception.ConflictException;
import re.edu.exception.ForbiddenException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.MentorMapper;
import re.edu.repository.MentorRepository;
import re.edu.repository.UserRepository;
import re.edu.service.MentorService;
import re.edu.util.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final MentorMapper mentorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginatedData<MentorResponse> getAllMentors(String currentUsername, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Mentor> mentors = mentorRepository.findAll(pageable);

        List<MentorResponse> items = mentors.getContent().stream()
                .map(mentorMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(mentors.getTotalPages())
                .totalItems(mentors.getTotalElements())
                .build();

        return PaginatedData.<MentorResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public MentorResponse getMentorById(Long id, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Mentor mentor = findById(id);

        // Validate that the user associated with this mentor has MENTOR role
        if (mentor.getUser().getRole() != Role.MENTOR) {
            throw new ResourceNotFoundException("Mentor không tồn tại với ID: " + id);
        }

        if (currentUser.getRole() == Role.MENTOR) {
            if (!mentor.getMentorId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể xem thông tin của mình");
            }
        }
        return mentorMapper.toResponse(mentor);
    }

    @Override
    @Transactional
    public MentorResponse createMentor(MentorRequest request) {
        // Check username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        // Check email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }

        // Create User first
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.MENTOR)
                .isActive(true)
                .build();
        User savedUser = userRepository.save(user);

        // Create Mentor
        Mentor mentor = Mentor.builder()
                .user(savedUser)
                .department(request.getDepartment())
                .academicRank(request.getAcademicRank())
                .build();

        return mentorMapper.toResponse(mentorRepository.save(mentor));
    }

    @Override
    public MentorResponse updateMentor(Long id, UpdateMentorRequest request, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Mentor mentor = findById(id);

        if (currentUser.getRole() == Role.MENTOR) {
            if (!mentor.getMentorId().equals(currentUser.getId())) {
                throw new ForbiddenException("Bạn chỉ có thể cập nhật thông tin của mình");
            }
        }

        if (request.getDepartment() != null) mentor.setDepartment(request.getDepartment());
        if (request.getAcademicRank() != null) mentor.setAcademicRank(request.getAcademicRank());

        return mentorMapper.toResponse(mentorRepository.save(mentor));
    }

    private Mentor findById(Long id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor không tồn tại với ID: " + id));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }
}
