package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.AssessmentRoundRequest;
import re.edu.dto.response.AssessmentRoundResponse;
import re.edu.dto.response.EvaluationCriteriaResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.AssessmentRound;
import re.edu.entity.EvaluationCriteria;
import re.edu.entity.InternshipPhase;
import re.edu.entity.RoundCriteria;
import re.edu.exception.BadRequestException;
import re.edu.exception.ConflictException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.AssessmentRoundMapper;
import re.edu.mapper.EvaluationCriteriaMapper;
import re.edu.repository.AssessmentRoundRepository;
import re.edu.repository.EvaluationCriteriaRepository;
import re.edu.repository.InternshipPhaseRepository;
import re.edu.repository.RoundCriteriaRepository;
import re.edu.service.AssessmentRoundService;
import re.edu.util.RoundStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentRoundServiceImpl implements AssessmentRoundService {

    private final AssessmentRoundRepository roundRepository;
    private final InternshipPhaseRepository phaseRepository;
    private final EvaluationCriteriaRepository criteriaRepository;
    private final RoundCriteriaRepository roundCriteriaRepository;
    private final AssessmentRoundMapper roundMapper;
    private final EvaluationCriteriaMapper criteriaMapper;

    @Override
    public PaginatedData<AssessmentRoundResponse> getAllRounds(int page, int pageSize, Long phaseId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<AssessmentRound> rounds;

        if (phaseId != null) {
            rounds = roundRepository.findByPhaseId(phaseId, pageable);
        } else {
            rounds = roundRepository.findAll(pageable);
        }

        List<AssessmentRoundResponse> items = rounds.getContent().stream()
                .map(this::toResponseWithCriteria)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(rounds.getTotalPages())
                .totalItems(rounds.getTotalElements())
                .build();

        return PaginatedData.<AssessmentRoundResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public AssessmentRoundResponse getRoundById(Long id) {
        AssessmentRound round = roundRepository.findByIdWithPhase(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đợt đánh giá với ID: " + id));
        return toResponseWithCriteria(round);
    }

    @Override
    @Transactional
    public AssessmentRoundResponse createRound(AssessmentRoundRequest request) {
        InternshipPhase phase = phaseRepository.findByIdWithDetails(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giai đoạn với ID: " + request.getPhaseId()));

        if (request.getStartDate().isBefore(phase.getStartDate()) ||
            request.getEndDate().isAfter(phase.getEndDate())) {
            throw new BadRequestException("Ngày đợt đánh giá phải nằm trong khoảng thời gian của giai đoạn");
        }

        if (roundRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên đợt đánh giá đã tồn tại");
        }

        if (request.getCriteriaIds() == null || request.getCriteriaIds().isEmpty()) {
            throw new BadRequestException("Danh sách tiêu chí không được để trống");
        }

        AssessmentRound round = roundMapper.toEntity(request);
        round.setPhase(phase);
        AssessmentRound savedRound = roundRepository.save(round);

        double defaultWeight = 1.0 / request.getCriteriaIds().size();
        for (Long criteriaId : request.getCriteriaIds()) {
            EvaluationCriteria criteria = criteriaRepository.findByIdWithDetails(criteriaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tiêu chí không tồn tại với ID: " + criteriaId));

            RoundCriteria roundCriteria = RoundCriteria.builder()
                    .round(savedRound)
                    .criteria(criteria)
                    .weight(defaultWeight)
                    .build();
            roundCriteriaRepository.save(roundCriteria);
        }

        return toResponseWithCriteria(savedRound);
    }

    @Override
    @Transactional
    public AssessmentRoundResponse updateRound(Long id, AssessmentRoundRequest request) {
        AssessmentRound round = roundRepository.findByIdWithPhase(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đợt đánh giá với ID: " + id));

        InternshipPhase phase = phaseRepository.findByIdWithDetails(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giai đoạn với ID: " + request.getPhaseId()));

        if (request.getStartDate().isBefore(phase.getStartDate()) ||
            request.getEndDate().isAfter(phase.getEndDate())) {
            throw new BadRequestException("Ngày đợt đánh giá phải nằm trong khoảng thời gian của giai đoạn");
        }

        if (!round.getName().equals(request.getName()) && roundRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên đợt đánh giá đã tồn tại");
        }

        roundMapper.updateEntity(request, round);
        round.setPhase(phase);
        AssessmentRound updated = roundRepository.save(round);

        if (request.getCriteriaIds() != null && !request.getCriteriaIds().isEmpty()) {
            roundCriteriaRepository.deleteByRoundId(id);
            double defaultWeight = 1.0 / request.getCriteriaIds().size();
            for (Long criteriaId : request.getCriteriaIds()) {
                EvaluationCriteria criteria = criteriaRepository.findByIdWithDetails(criteriaId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tiêu chí không tồn tại với ID: " + criteriaId));

                RoundCriteria roundCriteria = RoundCriteria.builder()
                        .round(updated)
                        .criteria(criteria)
                        .weight(defaultWeight)
                        .build();
                roundCriteriaRepository.save(roundCriteria);
            }
        }

        return toResponseWithCriteria(updated);
    }

    @Override
    @Transactional
    public void deleteRound(Long id) {
        AssessmentRound round = roundRepository.findByIdWithPhase(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đợt đánh giá với ID: " + id));

        roundCriteriaRepository.deleteByRoundId(id);

        roundRepository.delete(round);
    }

    private AssessmentRoundResponse toResponseWithCriteria(AssessmentRound round) {
        AssessmentRoundResponse response = roundMapper.toResponse(round);
        List<RoundCriteria> roundCriteria = roundCriteriaRepository.findByRoundIdWithDetails(round.getId());
        List<EvaluationCriteriaResponse> criteriaResponses = roundCriteria.stream()
                .map(rc -> criteriaMapper.toResponse(rc.getCriteria()))
                .collect(Collectors.toList());

        response.setCriteria(criteriaResponses);
        return response;
    }
}
