package re.edu.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class RoundCriteriaRequest {

    @NotNull(message = "Round ID không được để trống")
    private Long roundId;

    @NotNull(message = "Criteria ID không được để trống")
    private Long criteriaId;

    @NotNull(message = "Trọng số không được để trống")
    @Positive(message = "Trọng số phải lớn hơn 0")
    private Double weight;
}
