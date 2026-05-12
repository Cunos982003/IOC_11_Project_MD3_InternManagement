package re.edu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import re.edu.util.RoundStatus;
import re.edu.validation.DateRange;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DateRange(startDate = "startDate", endDate = "endDate", message = "Ngày bắt đầu phải trước ngày kết thúc")
public class AssessmentRoundRequest {

    @NotBlank(message = "Tên đợt đánh giá không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Phase ID không được để trống")
    private Long phaseId;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    private RoundStatus status;

    private List<Long> criteriaIds;
}
