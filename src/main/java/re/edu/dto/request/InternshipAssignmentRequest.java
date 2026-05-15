package re.edu.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipAssignmentRequest {

    @NotNull(message = "Student ID không được để trống")
    private Long studentId;

    @NotNull(message = "Mentor ID không được để trống")
    private Long mentorId;

    @NotNull(message = "Phase ID không được để trống")
    private Long phaseId;
}
