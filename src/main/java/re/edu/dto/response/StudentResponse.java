package re.edu.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String studentCode;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private Double gpa;
    private String major;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
