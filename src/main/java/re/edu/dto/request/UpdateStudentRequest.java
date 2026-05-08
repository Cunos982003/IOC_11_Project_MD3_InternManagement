package re.edu.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateStudentRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private Double gpa;
    private String major;
}
