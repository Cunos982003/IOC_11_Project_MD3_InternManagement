package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.AssessmentResultRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.AssessmentResultService;

@RestController
@RequestMapping("/api/assessment-results")
@RequiredArgsConstructor
public class AssessmentResultController {

    private final AssessmentResultService resultService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAllResults(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long assignmentId,
            @RequestParam(required = false) Long mentorId,
            @RequestParam(required = false) Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                resultService.getAllResults(page, pageSize, assignmentId, mentorId, studentId)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getResultById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(resultService.getResultById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ApiResponse<?>> createResult(
            @Valid @RequestBody AssessmentResultRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo kết quả đánh giá thành công",
                        resultService.createResult(request, userDetails.getUsername())));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ApiResponse<?>> updateResult(
            @PathVariable Long id,
            @Valid @RequestBody AssessmentResultRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật kết quả đánh giá thành công",
                resultService.updateResult(id, request, userDetails.getUsername())));
    }
}
