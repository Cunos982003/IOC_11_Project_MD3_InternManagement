package re.edu.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
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
