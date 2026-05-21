package re.edu.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.edu.dto.request.RoundCriteriaRequest;
import re.edu.dto.response.PaginatedData;
import re.edu.dto.response.RoundCriteriaResponse;
import re.edu.entity.AssessmentRound;
import re.edu.entity.EvaluationCriteria;
import re.edu.entity.RoundCriteria;
import re.edu.exception.ConflictException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.RoundCriteriaMapper;
import re.edu.repository.AssessmentRoundRepository;
import re.edu.repository.EvaluationCriteriaRepository;
import re.edu.repository.RoundCriteriaRepository;
import re.edu.service.RoundCriteriaService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundCriteriaServiceImpl implements RoundCriteriaService {

    private final RoundCriteriaRepository roundCriteriaRepository;
    private final AssessmentRoundRepository assessmentRoundRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final RoundCriteriaMapper roundCriteriaMapper;

    @Override
    public PaginatedData<RoundCriteriaResponse> getAllRoundCriteria(int page, int pageSize, Long roundId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Specification<RoundCriteria> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (roundId != null) {
                predicates.add(cb.equal(root.get("round").get("id"), roundId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<RoundCriteria> roundCriteriaPage = roundCriteriaRepository.findAll(spec, pageable);

        List<RoundCriteriaResponse> items = roundCriteriaPage.getContent().stream()
                .map(roundCriteriaMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(roundCriteriaPage.getTotalPages())
                .totalItems(roundCriteriaPage.getTotalElements())
                .build();

        return PaginatedData.<RoundCriteriaResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public RoundCriteriaResponse getRoundCriteriaById(Long id) {
        RoundCriteria roundCriteria = roundCriteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí đợt đánh giá với ID: " + id));
        return roundCriteriaMapper.toResponse(roundCriteria);
    }

    @Override
    @Transactional
    public RoundCriteriaResponse createRoundCriteria(RoundCriteriaRequest request) {
        AssessmentRound round = assessmentRoundRepository.findById(request.getRoundId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đợt đánh giá với ID: " + request.getRoundId()));
        EvaluationCriteria criteria = evaluationCriteriaRepository.findById(request.getCriteriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí với ID: " + request.getCriteriaId()));
        if (roundCriteriaRepository.existsByRoundIdAndCriteriaId(request.getRoundId(), request.getCriteriaId())) {
            throw new ConflictException("Tiêu chí này đã tồn tại trong đợt đánh giá");
        }
        RoundCriteria roundCriteria = RoundCriteria.builder()
                .round(round)
                .criteria(criteria)
                .weight(request.getWeight())
                .build();

        RoundCriteria saved = roundCriteriaRepository.save(roundCriteria);
        return roundCriteriaMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RoundCriteriaResponse updateRoundCriteria(Long id, RoundCriteriaRequest request) {
        RoundCriteria roundCriteria = roundCriteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí đợt đánh giá với ID: " + id));
        roundCriteria.setWeight(request.getWeight());
        RoundCriteria updated = roundCriteriaRepository.save(roundCriteria);
        return roundCriteriaMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteRoundCriteria(Long id) {
        RoundCriteria roundCriteria = roundCriteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí đợt đánh giá với ID: " + id));
        roundCriteriaRepository.delete(roundCriteria);
    }
}
