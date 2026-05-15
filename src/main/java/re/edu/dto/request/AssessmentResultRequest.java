package re.edu.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class AssessmentResultRequest {

    @NotNull(message = "Assignment ID không được để trống")
    private Long assignmentId;

    @NotNull(message = "Round ID không được để trống")
    private Long roundId;

    @NotNull(message = "Criterion ID không được để trống")
    private Long criterionId;

    @NotNull(message = "Điểm không được để trống")
    @PositiveOrZero(message = "Điểm phải lớn hơn hoặc bằng 0")
    private Double score;

    private String comments;
}
