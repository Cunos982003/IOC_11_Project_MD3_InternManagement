package re.edu.dto.response;

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
public class RoundCriteriaResponse {

    private Long id;
    private Long roundId;
    private String roundName;
    private Long criteriaId;
    private String criteriaName;
    private String criteriaDescription;
    private Double maxScore;
    private Double weight;
}
