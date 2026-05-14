package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.EvaluationCriteriaRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.EvaluationCriteriaService;

@RestController
@RequestMapping("/api/evaluation-criteria")
@RequiredArgsConstructor
public class EvaluationCriteriaController {

    private final EvaluationCriteriaService criteriaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAllCriteria(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(ApiResponse.success(criteriaService.getAllCriteria(page, pageSize)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getCriteriaById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(criteriaService.getCriteriaById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createCriteria(@Valid @RequestBody EvaluationCriteriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo tiêu chí thành công", criteriaService.createCriteria(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateCriteria(@PathVariable Long id,
                                                         @Valid @RequestBody EvaluationCriteriaRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật tiêu chí thành công",
                criteriaService.updateCriteria(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteCriteria(@PathVariable Long id) {
        criteriaService.deleteCriteria(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa tiêu chí thành công"));
    }
}
