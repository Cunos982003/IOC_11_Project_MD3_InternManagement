package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.InternshipAssignmentRequest;
import re.edu.dto.request.UpdateAssignmentStatusRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.InternshipAssignmentService;

@RestController
@RequestMapping("/api/internship-assignments")
@RequiredArgsConstructor
public class InternshipAssignmentController {

    private final InternshipAssignmentService assignmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAllAssignments(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long mentorId,
            @RequestParam(required = false) Long phaseId) {
        return ResponseEntity.ok(ApiResponse.success(
                assignmentService.getAllAssignments(userDetails.getUsername(), page, pageSize, studentId, mentorId, phaseId)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAssignmentById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(assignmentService.getAssignmentById(id, userDetails.getUsername())));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createAssignment(@Valid @RequestBody InternshipAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo phân công thực tập thành công",
                        assignmentService.createAssignment(request)));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateAssignmentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAssignmentStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái phân công thành công",
                assignmentService.updateAssignmentStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa phân công thành công"));
    }
}
