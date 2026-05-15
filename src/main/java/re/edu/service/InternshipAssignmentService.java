package re.edu.service;

import re.edu.dto.request.InternshipAssignmentRequest;
import re.edu.dto.request.UpdateAssignmentStatusRequest;
import re.edu.dto.response.InternshipAssignmentResponse;
import re.edu.dto.response.PaginatedData;

public interface InternshipAssignmentService {

    PaginatedData<InternshipAssignmentResponse> getAllAssignments(String currentUsername, int page, int pageSize, Long studentId, Long mentorId, Long phaseId);

    InternshipAssignmentResponse getAssignmentById(Long id, String currentUsername);

    InternshipAssignmentResponse createAssignment(InternshipAssignmentRequest request);

    InternshipAssignmentResponse updateAssignmentStatus(Long id, UpdateAssignmentStatusRequest request);

    void deleteAssignment(Long id);
}
