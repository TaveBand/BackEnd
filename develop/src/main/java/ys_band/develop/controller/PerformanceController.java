package ys_band.develop.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.performance.PerformanceCreateDto;
import ys_band.develop.dto.performance.PerformanceDto;
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
    public ResponseEntity<List<PerformanceDto>> getAllPerformances() {
        List<PerformanceDto> performances = performanceService.findAllPerformances();
        return ResponseEntity.ok(performances);
    }

    @GetMapping("/{performance_id}")
    public ResponseEntity<PerformanceDto> getPerformanceById(@PathVariable("performance_id") Long performanceId) {
        Optional<PerformanceDto> performance = performanceService.findPerformanceById(performanceId);
        return performance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<PerformanceDto> savePerformance(@RequestBody PerformanceCreateDto performanceCreateDto) {
        PerformanceDto savedPerformance = performanceService.savePerformance(performanceCreateDto);
        return ResponseEntity.ok(savedPerformance);
    }

    @DeleteMapping("/delete/{performance_id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable("performance_id") Long performanceId) {
        performanceService.deletePerformance(performanceId);
        return ResponseEntity.noContent().build();
    }

}
