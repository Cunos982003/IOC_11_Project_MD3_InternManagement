package re.edu.service;

import re.edu.dto.request.AssessmentResultRequest;
import re.edu.dto.response.AssessmentResultResponse;
import re.edu.dto.response.PaginatedData;

public interface AssessmentResultService {

    PaginatedData<AssessmentResultResponse> getAllResults(int page, int pageSize, Long assignmentId, Long mentorId, Long studentId);

    AssessmentResultResponse createResult(AssessmentResultRequest request, String mentorUsername);

    AssessmentResultResponse updateResult(Long id, AssessmentResultRequest request, String mentorUsername);
}
