package re.edu.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MentorResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String department;
    private String phone;
    private String specialization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
