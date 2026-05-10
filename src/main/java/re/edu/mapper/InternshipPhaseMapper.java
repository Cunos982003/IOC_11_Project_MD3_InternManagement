package re.edu.mapper;

import org.springframework.stereotype.Component;
import re.edu.dto.request.InternshipPhaseRequest;
import re.edu.dto.response.InternshipPhaseResponse;
import re.edu.entity.InternshipPhase;

@Component
public class InternshipPhaseMapper {

    public InternshipPhaseResponse toResponse(InternshipPhase phase) {
        return InternshipPhaseResponse.builder()
                .id(phase.getId())
                .name(phase.getName())
                .description(phase.getDescription())
                .startDate(phase.getStartDate())
                .endDate(phase.getEndDate())
                .status(phase.getStatus())
                .createdAt(phase.getCreatedAt())
                .updatedAt(phase.getUpdatedAt())
                .build();
    }

    public InternshipPhase toEntity(InternshipPhaseRequest request) {
        return InternshipPhase.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
    }

    public void updateEntity(InternshipPhaseRequest request, InternshipPhase phase) {
        phase.setName(request.getName());
        phase.setDescription(request.getDescription());
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        phase.setStatus(request.getStatus());
    }
}
