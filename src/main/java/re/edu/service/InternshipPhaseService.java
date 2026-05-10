package re.edu.service;

import re.edu.dto.request.InternshipPhaseRequest;
import re.edu.dto.response.InternshipPhaseResponse;
import re.edu.dto.response.PaginatedData;

public interface InternshipPhaseService {
    PaginatedData<InternshipPhaseResponse> getAllPhases(int page, int pageSize);
    InternshipPhaseResponse getPhaseById(Long id);
    InternshipPhaseResponse createPhase(InternshipPhaseRequest request);
    InternshipPhaseResponse updatePhase(Long id, InternshipPhaseRequest request);
    void deletePhase(Long id);
}
