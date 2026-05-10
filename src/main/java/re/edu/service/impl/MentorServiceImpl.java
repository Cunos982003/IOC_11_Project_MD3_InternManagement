package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

        if (currentUser.getRole() == Role.MENTOR) {
            Mentor ownMentor = mentorRepository.findByUserId(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ mentor"));
            if (!ownMentor.getId().equals(id)) {
                throw new ForbiddenException("Bạn chỉ có thể xem thông tin của mình");
            }
        }
        return mentorMapper.toResponse(mentor);
    }

    @Override
    public MentorResponse createMentor(MentorRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại với ID: " + request.getUserId()));

        if (user.getRole() != Role.MENTOR) {
            throw new ConflictException("Người dùng phải có vai trò MENTOR");
        }
        if (mentorRepository.existsByUserId(request.getUserId())) {
            throw new ConflictException("Người dùng này đã có hồ sơ mentor");
        }

        Mentor mentor = Mentor.builder()
                .user(user)
                .fullName(request.getFullName())
                .department(request.getDepartment())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .build();
        return mentorMapper.toResponse(mentorRepository.save(mentor));
    }

    @Override
    public MentorResponse updateMentor(Long id, UpdateMentorRequest request, String currentUsername) {
        User currentUser = findUserByUsername(currentUsername);
        Mentor mentor = findById(id);

        if (currentUser.getRole() == Role.MENTOR) {
            Mentor ownMentor = mentorRepository.findByUserId(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ mentor"));
            if (!ownMentor.getId().equals(id)) {
                throw new ForbiddenException("Bạn chỉ có thể cập nhật thông tin của mình");
            }
        }

        if (request.getFullName() != null) mentor.setFullName(request.getFullName());
        if (request.getDepartment() != null) mentor.setDepartment(request.getDepartment());
        if (request.getPhone() != null) mentor.setPhone(request.getPhone());
        if (request.getSpecialization() != null) mentor.setSpecialization(request.getSpecialization());

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
