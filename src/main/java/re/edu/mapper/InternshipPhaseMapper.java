package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.request.InternshipPhaseRequest;
import re.edu.dto.response.InternshipPhaseResponse;
import re.edu.entity.InternshipPhase;

@Component
@RequiredArgsConstructor
public class InternshipPhaseMapper {

    private final ModelMapper modelMapper;

    public InternshipPhaseResponse toResponse(InternshipPhase phase) {
        return modelMapper.map(phase, InternshipPhaseResponse.class);
    }

    public InternshipPhase toEntity(InternshipPhaseRequest request) {
        return modelMapper.map(request, InternshipPhase.class);
    }

    public void updateEntity(InternshipPhaseRequest request, InternshipPhase phase) {
        modelMapper.map(request, phase);
    }
}
