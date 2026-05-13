package re.edu.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationCriteriaRequest {

    @NotBlank(message = "Tên tiêu chí không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Điểm tối đa không được để trống")
    @Positive(message = "Điểm tối đa phải lớn hơn 0")
    private Double maxScore;
}
