package re.edu.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.util.AssignmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAssignmentStatusRequest {

    @NotNull(message = "Status không được để trống")
    private AssignmentStatus status;
}
