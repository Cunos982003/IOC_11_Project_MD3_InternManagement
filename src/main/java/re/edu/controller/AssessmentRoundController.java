package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.AssessmentRoundRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.AssessmentRoundService;

@RestController
@RequestMapping("/api/assessment-rounds")
@RequiredArgsConstructor
public class AssessmentRoundController {

    private final AssessmentRoundService roundService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getAllRounds(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long phaseId) {
        return ResponseEntity.ok(ApiResponse.success(roundService.getAllRounds(page, pageSize, phaseId)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> getRoundById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roundService.getRoundById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createRound(@Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo đợt đánh giá thành công", roundService.createRound(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateRound(
            @PathVariable Long id,
            @Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật đợt đánh giá thành công",
                roundService.updateRound(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteRound(@PathVariable Long id) {
        roundService.deleteRound(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa đợt đánh giá thành công"));
    }
}
