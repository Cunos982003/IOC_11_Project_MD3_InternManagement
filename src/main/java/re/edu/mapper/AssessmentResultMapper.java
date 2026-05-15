package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.response.AssessmentResultResponse;
import re.edu.entity.AssessmentResult;

@Component
@RequiredArgsConstructor
public class AssessmentResultMapper {

    private final ModelMapper modelMapper;

    public AssessmentResultResponse toResponse(AssessmentResult entity) {
        if (entity == null) {
            return null;
        }

        AssessmentResultResponse response = modelMapper.map(entity, AssessmentResultResponse.class);
        if (entity.getAssignment() != null) {
            response.setAssignmentId(entity.getAssignment().getId());

            if (entity.getAssignment().getStudent() != null) {
                response.setStudentId(entity.getAssignment().getStudent().getStudentId());

                if (entity.getAssignment().getStudent().getUser() != null) {
                    response.setStudentName(entity.getAssignment().getStudent().getUser().getFullName());
                }
            }
        }

        if (entity.getRound() != null) {
            response.setRoundId(entity.getRound().getId());
            response.setRoundName(entity.getRound().getName());
        }

        if (entity.getCriterion() != null) {
            response.setCriterionId(entity.getCriterion().getId());
            response.setCriterionName(entity.getCriterion().getName());
            response.setCriterionDescription(entity.getCriterion().getDescription());
            response.setMaxScore(entity.getCriterion().getMaxScore());
        }

        if (entity.getEvaluatedBy() != null) {
            response.setEvaluatedById(entity.getEvaluatedBy().getId());
            response.setEvaluatedByName(entity.getEvaluatedBy().getFullName());
        }

        return response;
    }
}
