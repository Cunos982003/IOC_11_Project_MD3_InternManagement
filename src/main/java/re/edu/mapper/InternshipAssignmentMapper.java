package re.edu.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import re.edu.dto.response.InternshipAssignmentResponse;
import re.edu.entity.InternshipAssignment;

@Component
@RequiredArgsConstructor
public class InternshipAssignmentMapper {

    private final ModelMapper modelMapper;

    public InternshipAssignmentResponse toResponse(InternshipAssignment entity) {
        if (entity == null) {
            return null;
        }

        InternshipAssignmentResponse response = modelMapper.map(entity, InternshipAssignmentResponse.class);
        if (entity.getStudent() != null) {
            response.setStudentId(entity.getStudent().getStudentId());

            if (entity.getStudent().getUser() != null) {
                response.setStudentName(entity.getStudent().getUser().getUsername());
                response.setStudentEmail(entity.getStudent().getUser().getEmail());
            }
        }

        if (entity.getMentor() != null) {
            response.setMentorId(entity.getMentor().getMentorId());

            if (entity.getMentor().getUser() != null) {
                response.setMentorName(entity.getMentor().getUser().getUsername());
                response.setMentorEmail(entity.getMentor().getUser().getEmail());
            }
        }

        if (entity.getPhase() != null) {
            response.setPhaseId(entity.getPhase().getId());
            response.setPhaseName(entity.getPhase().getName());
        }

        return response;
    }
}
