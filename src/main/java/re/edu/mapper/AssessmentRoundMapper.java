package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.request.AssessmentRoundRequest;
import re.edu.dto.response.AssessmentRoundResponse;
import re.edu.entity.AssessmentRound;

@Component
@RequiredArgsConstructor
public class AssessmentRoundMapper {

    private final ModelMapper modelMapper;
    private final EvaluationCriteriaMapper criteriaMapper;

    public AssessmentRoundResponse toResponse(AssessmentRound round) {
        AssessmentRoundResponse response = modelMapper.map(round, AssessmentRoundResponse.class);
        if (round.getPhase() != null) {
            response.setPhaseId(round.getPhase().getId());
            response.setPhaseName(round.getPhase().getName());
        }
        return response;
    }

    public AssessmentRound toEntity(AssessmentRoundRequest request) {
        return modelMapper.map(request, AssessmentRound.class);
    }

    public void updateEntity(AssessmentRoundRequest request, AssessmentRound round) {
        modelMapper.map(request, round);
    }
}
