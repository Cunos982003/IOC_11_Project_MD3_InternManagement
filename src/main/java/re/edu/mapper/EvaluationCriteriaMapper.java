package re.edu.mapper;

import org.springframework.stereotype.Component;
import re.edu.dto.request.EvaluationCriteriaRequest;
import re.edu.dto.response.EvaluationCriteriaResponse;
import re.edu.entity.EvaluationCriteria;

@Component
public class EvaluationCriteriaMapper {

    public EvaluationCriteriaResponse toResponse(EvaluationCriteria criteria) {
        return EvaluationCriteriaResponse.builder()
                .id(criteria.getId())
                .name(criteria.getName())
                .description(criteria.getDescription())
                .maxScore(criteria.getMaxScore())
                .createdAt(criteria.getCreatedAt())
                .updatedAt(criteria.getUpdatedAt())
                .build();
    }

    public EvaluationCriteria toEntity(EvaluationCriteriaRequest request) {
        return EvaluationCriteria.builder()
                .name(request.getName())
                .description(request.getDescription())
                .maxScore(request.getMaxScore())
                .build();
    }

    public void updateEntity(EvaluationCriteriaRequest request, EvaluationCriteria criteria) {
        criteria.setName(request.getName());
        criteria.setDescription(request.getDescription());
        criteria.setMaxScore(request.getMaxScore());
    }
}
