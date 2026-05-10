package re.edu.service;

import re.edu.dto.request.MentorRequest;
import re.edu.dto.request.UpdateMentorRequest;
import re.edu.dto.response.MentorResponse;
import re.edu.dto.response.PaginatedData;

public interface MentorService {
    PaginatedData<MentorResponse> getAllMentors(String currentUsername, int page, int pageSize);
    MentorResponse getMentorById(Long id, String currentUsername);
    MentorResponse createMentor(MentorRequest request);
    MentorResponse updateMentor(Long id, UpdateMentorRequest request, String currentUsername);
}
