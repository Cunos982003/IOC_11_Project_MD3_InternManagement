package re.edu.service;

import re.edu.dto.request.AssessmentRoundRequest;
import re.edu.dto.response.AssessmentRoundResponse;
import re.edu.dto.response.PaginatedData;

public interface AssessmentRoundService {
    PaginatedData<AssessmentRoundResponse> getAllRounds(int page, int pageSize, Long phaseId);
    AssessmentRoundResponse getRoundById(Long id);
    AssessmentRoundResponse createRound(AssessmentRoundRequest request);
    AssessmentRoundResponse updateRound(Long id, AssessmentRoundRequest request);
    void deleteRound(Long id);
}
