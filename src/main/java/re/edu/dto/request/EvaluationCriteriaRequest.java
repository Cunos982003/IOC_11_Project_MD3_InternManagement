package re.edu.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import re.edu.util.CriteriaType;

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

    @NotNull(message = "Trọng số không được để trống")
    @Min(value = 0, message = "Trọng số phải từ 0 đến 100")
    @Max(value = 100, message = "Trọng số phải từ 0 đến 100")
    private Double weight;

    @NotNull(message = "Loại tiêu chí không được để trống")
    private CriteriaType criteriaType;
}
