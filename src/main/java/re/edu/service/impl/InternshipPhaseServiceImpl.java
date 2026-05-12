package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import re.edu.dto.request.InternshipPhaseRequest;
import re.edu.dto.response.InternshipPhaseResponse;
import re.edu.dto.response.PaginatedData;
import re.edu.entity.InternshipPhase;
import re.edu.exception.BadRequestException;
import re.edu.exception.ConflictException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.InternshipPhaseMapper;
import re.edu.repository.InternshipPhaseRepository;
import re.edu.service.InternshipPhaseService;
import re.edu.util.Constants;
import re.edu.util.PhaseStatus;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPhaseServiceImpl implements InternshipPhaseService {

    private final InternshipPhaseRepository phaseRepository;
    private final InternshipPhaseMapper phaseMapper;

    @Override
    public PaginatedData<InternshipPhaseResponse> getAllPhases(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<InternshipPhase> phases = phaseRepository.findAll(pageable);

        List<InternshipPhaseResponse> items = phases.getContent().stream()
                .map(phaseMapper::toResponse)
                .toList();

        PaginatedData.Pagination pagination = PaginatedData.Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages(phases.getTotalPages())
                .totalItems(phases.getTotalElements())
                .build();

        return PaginatedData.<InternshipPhaseResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }

    @Override
    public InternshipPhaseResponse getPhaseById(Long id) {
        InternshipPhase phase = findById(id);
        return phaseMapper.toResponse(phase);
    }

    @Override
    public InternshipPhaseResponse createPhase(InternshipPhaseRequest request) {
        if (request.getStartDate() != null && request.getStartDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Ngày bắt đầu phải từ hôm nay trở đi");
        }

        if (phaseRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên giai đoạn đã tồn tại");
        }

        InternshipPhase phase = phaseMapper.toEntity(request);
        if (phase.getStatus() == null) {
            phase.setStatus(PhaseStatus.ACTIVE);
        }
        InternshipPhase saved = phaseRepository.save(phase);
        return phaseMapper.toResponse(saved);
    }

    @Override
    public InternshipPhaseResponse updatePhase(Long id, InternshipPhaseRequest request) {
        InternshipPhase phase = findById(id);

        if (PhaseStatus.COMPLETED.equals(phase.getStatus())) {
            throw new ConflictException(Constants.ERROR_PHASE_COMPLETED);
        }

        if (!phase.getName().equals(request.getName()) && phaseRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên giai đoạn đã tồn tại");
        }

        phaseMapper.updateEntity(request, phase);
        InternshipPhase updated = phaseRepository.save(phase);
        return phaseMapper.toResponse(updated);
    }

    @Override
    public void deletePhase(Long id) {
        InternshipPhase phase = findById(id);
        phaseRepository.delete(phase);
    }

    private InternshipPhase findById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ERROR_PHASE_NOT_FOUND));
    }
}
