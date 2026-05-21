package re.edu.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.AssessmentResultRequest;
import re.edu.dto.response.AssessmentResultResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.*;
import re.edu.exception.BadRequestException;
import re.edu.exception.ConflictException;
import re.edu.exception.ForbiddenException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.AssessmentResultMapper;
import re.edu.repository.*;
import re.edu.service.AssessmentResultService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentResultServiceImpl implements AssessmentResultService {

    private final AssessmentResultRepository resultRepository;
    private final InternshipAssignmentRepository assignmentRepository;
    private final AssessmentRoundRepository roundRepository;
    private final EvaluationCriteriaRepository criteriaRepository;
    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final AssessmentResultMapper resultMapper;

    @Override
    public PaginatedData<AssessmentResultResponse> getAllResults(
            int page, int pageSize, Long assignmentId, Long mentorId, Long studentId) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Specification<AssessmentResult> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (assignmentId != null) {
                predicates.add(cb.equal(root.get("assignment").get("id"), assignmentId));
            }

            if (mentorId != null) {
                predicates.add(cb.equal(root.get("assignment").get("mentor").get("mentorId"), mentorId));
            }

            if (studentId != null) {
                predicates.add(cb.equal(root.get("assignment").get("student").get("studentId"), studentId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<AssessmentResult> results = resultRepository.findAll(spec, pageable);

        List<AssessmentResultResponse> items = results.getContent().stream()
                .map(resultMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(results.getTotalPages())
                .totalItems(results.getTotalElements())
                .build();

        return PaginatedData.<AssessmentResultResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }


    @Override
    public AssessmentResultResponse getResultById(Long id) {
        AssessmentResult result = resultRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kết quả đánh giá với ID: " + id));
        return resultMapper.toResponse(result);
    }

    @Override
    @Transactional
    public AssessmentResultResponse createResult(AssessmentResultRequest request, String mentorUsername) {
        User mentorUser = userRepository.findByUsername(mentorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        Mentor mentor = mentorRepository.findByIdWithUser(mentorUser.getId())
                .orElseThrow(() -> new ForbiddenException("Chỉ giáo viên mới có thể đánh giá"));

        InternshipAssignment assignment = assignmentRepository.findByIdWithDetails(request.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phân công với ID: " + request.getAssignmentId()));

        if (!assignment.getMentor().getMentorId().equals(mentor.getMentorId())) {
            throw new ForbiddenException("Bạn không được phân công đánh giá sinh viên này");
        }

        AssessmentRound round = roundRepository.findByIdWithPhase(request.getRoundId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đợt đánh giá với ID: " + request.getRoundId()));

        EvaluationCriteria criterion = criteriaRepository.findByIdWithDetails(request.getCriterionId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí với ID: " + request.getCriterionId()));

        if (request.getScore() > criterion.getMaxScore()) {
            throw new BadRequestException("Điểm không được vượt quá điểm tối đa: " + criterion.getMaxScore());
        }

        if (resultRepository.existsByAssignmentIdAndRoundIdAndCriterionId(
                request.getAssignmentId(), request.getRoundId(), request.getCriterionId())) {
            throw new ConflictException("Đã tồn tại đánh giá cho tiêu chí này trong đợt đánh giá này");
        }

        AssessmentResult result = AssessmentResult.builder()
                .assignment(assignment)
                .round(round)
                .criterion(criterion)
                .score(request.getScore())
                .comments(request.getComments())
                .evaluatedBy(mentorUser)
                .evaluationDate(LocalDateTime.now())
                .build();

        AssessmentResult saved = resultRepository.save(result);
        return resultMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AssessmentResultResponse updateResult(Long id, AssessmentResultRequest request, String mentorUsername) {
        AssessmentResult result = resultRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kết quả đánh giá với ID: " + id));

        User mentorUser = userRepository.findByUsername(mentorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        Mentor mentor = mentorRepository.findByIdWithUser(mentorUser.getId())
                .orElseThrow(() -> new ForbiddenException("Chỉ giáo viên mới có thể đánh giá"));

        if (!result.getEvaluatedBy().getId().equals(mentorUser.getId())) {
            throw new ForbiddenException("Bạn chỉ có thể cập nhật đánh giá của chính mình");
        }

        if (request.getScore() > result.getCriterion().getMaxScore()) {
            throw new BadRequestException("Điểm không được vượt quá điểm tối đa: "
                    + result.getCriterion().getMaxScore());
        }

        result.setScore(request.getScore());
        result.setComments(request.getComments());
        result.setEvaluationDate(LocalDateTime.now());

        AssessmentResult updated = resultRepository.save(result);
        return resultMapper.toResponse(updated);
    }
}
