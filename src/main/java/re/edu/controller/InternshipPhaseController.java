package re.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.edu.dto.request.InternshipPhaseRequest;
import re.edu.dto.response.ApiResponse;
import re.edu.service.InternshipPhaseService;

@RestController
@RequestMapping("/api/phases")
@RequiredArgsConstructor
public class InternshipPhaseController {

    private final InternshipPhaseService phaseService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAllPhases(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(ApiResponse.success(phaseService.getAllPhases(page, pageSize)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> getPhaseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(phaseService.getPhaseById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createPhase(@Valid @RequestBody InternshipPhaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo giai đoạn thành công", phaseService.createPhase(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updatePhase(@PathVariable Long id,
                                                      @Valid @RequestBody InternshipPhaseRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật giai đoạn thành công",
                phaseService.updatePhase(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa giai đoạn thành công"));
    }
}
