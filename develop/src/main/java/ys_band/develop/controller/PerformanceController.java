package ys_band.develop.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.performance.PerformancePostDto;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.service.PerformanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dailband/boards/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    public ResponseEntity<List<PerformanceGetDto>> getAllPerformances() {
        List<PerformanceGetDto> performances = performanceService.findAllPerformances();
        return ResponseEntity.ok(performances);
    }

    @GetMapping("/{performance_id}")
    public ResponseEntity<PerformanceGetDto> getPerformanceById(@PathVariable("performance_id") Long performanceId) {
        Optional<PerformanceGetDto> performance = performanceService.findPerformanceById(performanceId);
        return performance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerformanceGetDto> savePerformance(@RequestBody PerformancePostDto performancePostDto) {
        PerformanceGetDto savedPerformance = performanceService.savePerformance(performancePostDto);
        return ResponseEntity.ok(savedPerformance);
    }

    @DeleteMapping("/{performance_id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable("performance_id") Long performanceId) {
        performanceService.deletePerformance(performanceId);
        return ResponseEntity.noContent().build();
    }


    /**
     * 현재 세션의 사용자 ID로 해당 사용자가 소유한 공연들을 조회합니다.
     *
     * @param session 현재 HTTP 세션
     * @return 해당 사용자가 소유한 공연 목록
     */
    @GetMapping("/user/myperformance")
    public ResponseEntity<List<PerformanceGetDto>> getMyPerformances(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        List<PerformanceGetDto> performances = performanceService.findPerformancesByUserId(userId);
        return ResponseEntity.ok(performances);
    }
}
