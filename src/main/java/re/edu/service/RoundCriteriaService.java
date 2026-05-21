package re.edu.service;

import re.edu.dto.request.RoundCriteriaRequest;
import re.edu.dto.response.RoundCriteriaResponse;

import java.util.List;

public interface RoundCriteriaService {

    List<RoundCriteriaResponse> getAllRoundCriteria(Long roundId);

    RoundCriteriaResponse getRoundCriteriaById(Long id);

    RoundCriteriaResponse createRoundCriteria(RoundCriteriaRequest request);

    RoundCriteriaResponse updateRoundCriteria(Long id, RoundCriteriaRequest request);

    void deleteRoundCriteria(Long id);
}
