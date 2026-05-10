package re.edu.service;

import re.edu.dto.request.EvaluationCriteriaRequest;
import re.edu.dto.response.EvaluationCriteriaResponse;
import re.edu.dto.response.PaginatedData;

public interface EvaluationCriteriaService {
    PaginatedData<EvaluationCriteriaResponse> getAllCriteria(int page, int pageSize);
    EvaluationCriteriaResponse getCriteriaById(Long id);
    EvaluationCriteriaResponse createCriteria(EvaluationCriteriaRequest request);
    EvaluationCriteriaResponse updateCriteria(Long id, EvaluationCriteriaRequest request);
    void deleteCriteria(Long id);
}
