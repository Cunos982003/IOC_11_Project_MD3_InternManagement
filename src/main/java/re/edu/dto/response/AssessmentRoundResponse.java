package re.edu.dto.response;

import lombok.*;
import re.edu.util.RoundStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRoundResponse {
    private Long id;
    private String name;
    private String description;
    private Long phaseId;
    private String phaseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private RoundStatus status;
    private List<EvaluationCriteriaResponse> criteria;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
