package re.edu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMentorRequest {
    private String fullName;
    private String department;
    private String phone;
    private String specialization;
}
