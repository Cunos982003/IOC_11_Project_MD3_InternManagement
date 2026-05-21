package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.RoundCriteriaRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.RoundCriteriaService;

@RestController
@RequestMapping("/api/round-criteria")
@RequiredArgsConstructor
public class RoundCriteriaController {

    private final RoundCriteriaService roundCriteriaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAllRoundCriteria(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long roundId) {
        return ResponseEntity.ok(ApiResponse.success(roundCriteriaService.getAllRoundCriteria(page, pageSize, roundId)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getRoundCriteriaById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roundCriteriaService.getRoundCriteriaById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createRoundCriteria(@Valid @RequestBody RoundCriteriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Thêm tiêu chí vào đợt đánh giá thành công",
                        roundCriteriaService.createRoundCriteria(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateRoundCriteria(
            @PathVariable Long id,
            @Valid @RequestBody RoundCriteriaRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật trọng số tiêu chí thành công",
                roundCriteriaService.updateRoundCriteria(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteRoundCriteria(@PathVariable Long id) {
        roundCriteriaService.deleteRoundCriteria(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa tiêu chí khỏi đợt đánh giá thành công"));
    }
}
