package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.request.RoundCriteriaRequest;
import re.edu.dto.response.RoundCriteriaResponse;
import re.edu.entity.RoundCriteria;

@Component
@RequiredArgsConstructor
public class RoundCriteriaMapper {

    private final ModelMapper modelMapper;

    public RoundCriteriaResponse toResponse(RoundCriteria entity) {
        if (entity == null) {
            return null;
        }

        RoundCriteriaResponse response = modelMapper.map(entity, RoundCriteriaResponse.class);
        if (entity.getRound() != null) {
            response.setRoundId(entity.getRound().getId());
            response.setRoundName(entity.getRound().getName());
        }

        if (entity.getCriteria() != null) {
            response.setCriteriaId(entity.getCriteria().getId());
            response.setCriteriaName(entity.getCriteria().getName());
            response.setCriteriaDescription(entity.getCriteria().getDescription());
            response.setMaxScore(entity.getCriteria().getMaxScore());
        }

        return response;
    }

    public RoundCriteria toEntity(RoundCriteriaRequest request) {
        if (request == null) {
            return null;
        }

        return modelMapper.map(request, RoundCriteria.class);
    }
}
