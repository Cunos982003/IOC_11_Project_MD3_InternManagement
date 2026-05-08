package re.edu.service;

import re.edu.dto.request.MentorRequest;
import re.edu.dto.request.UpdateMentorRequest;
import re.edu.dto.response.MentorResponse;

import java.util.List;

public interface MentorService {
    List<MentorResponse> getAllMentors(String currentUsername);
    MentorResponse getMentorById(Long id, String currentUsername);
    MentorResponse createMentor(MentorRequest request);
    MentorResponse updateMentor(Long id, UpdateMentorRequest request, String currentUsername);
}
