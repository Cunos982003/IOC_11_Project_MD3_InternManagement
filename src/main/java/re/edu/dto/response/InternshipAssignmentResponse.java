package re.edu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.util.AssignmentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipAssignmentResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long mentorId;
    private String mentorName;
    private String mentorEmail;
    private Long phaseId;
    private String phaseName;
    private AssignmentStatus status;
    private LocalDateTime assignedAt;
}
