package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import re.edu.dto.request.EvaluationCriteriaRequest;
import re.edu.dto.response.EvaluationCriteriaResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.EvaluationCriteria;
import re.edu.exception.ConflictException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.EvaluationCriteriaMapper;
import re.edu.repository.EvaluationCriteriaRepository;
import re.edu.service.EvaluationCriteriaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    private final EvaluationCriteriaRepository criteriaRepository;
    private final EvaluationCriteriaMapper criteriaMapper;

    @Override
    public PaginatedData<EvaluationCriteriaResponse> getAllCriteria(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<EvaluationCriteria> criteria = criteriaRepository.findAll(pageable);

        List<EvaluationCriteriaResponse> items = criteria.getContent().stream()
                .map(criteriaMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(criteria.getTotalPages())
                .totalItems(criteria.getTotalElements())
                .build();

        return PaginatedData.<EvaluationCriteriaResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public EvaluationCriteriaResponse getCriteriaById(Long id) {
        EvaluationCriteria criteria = criteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí với ID: " + id));
        return criteriaMapper.toResponse(criteria);
    }

    @Override
    public EvaluationCriteriaResponse createCriteria(EvaluationCriteriaRequest request) {
        if (criteriaRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên tiêu chí đã tồn tại");
        }

        EvaluationCriteria criteria = criteriaMapper.toEntity(request);
        EvaluationCriteria saved = criteriaRepository.save(criteria);
        return criteriaMapper.toResponse(saved);
    }

    @Override
    public EvaluationCriteriaResponse updateCriteria(Long id, EvaluationCriteriaRequest request) {
        EvaluationCriteria criteria = criteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí với ID: " + id));

        if (!criteria.getName().equals(request.getName()) && criteriaRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên tiêu chí đã tồn tại");
        }

        criteriaMapper.updateEntity(request, criteria);
        EvaluationCriteria updated = criteriaRepository.save(criteria);
        return criteriaMapper.toResponse(updated);
    }

    @Override
    public void deleteCriteria(Long id) {
        EvaluationCriteria criteria = criteriaRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tiêu chí với ID: " + id));
        criteriaRepository.delete(criteria);
    }
}
