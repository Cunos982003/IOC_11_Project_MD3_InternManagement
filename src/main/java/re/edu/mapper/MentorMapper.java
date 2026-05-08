package re.edu.mapper;

import org.springframework.stereotype.Component;
import re.edu.dto.response.MentorResponse;
import re.edu.entity.Mentor;

@Component
public class MentorMapper {

    public MentorResponse toResponse(Mentor mentor) {
        return MentorResponse.builder()
                .id(mentor.getId())
                .userId(mentor.getUser().getId())
                .username(mentor.getUser().getUsername())
                .email(mentor.getUser().getEmail())
                .fullName(mentor.getFullName())
                .department(mentor.getDepartment())
                .phone(mentor.getPhone())
                .specialization(mentor.getSpecialization())
                .createdAt(mentor.getCreatedAt())
                .updatedAt(mentor.getUpdatedAt())
                .build();
    }
}
