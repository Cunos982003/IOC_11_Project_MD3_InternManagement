package re.edu.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
