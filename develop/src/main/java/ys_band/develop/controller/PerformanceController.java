package ys_band.develop.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.performance.PerformancePostDto;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.service.Post.PerformanceService;

import java.util.List;
import java.util.Optional;

@RestController
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    /**
     *  /dailband/boards/performances
     *  공연 전부 조회하기. -> 모든 공연들을 반환시킨다
     * @return
     */
    @GetMapping("/dailband/boards/performances")
    public ResponseEntity<List<PerformanceGetDto>> getAllPerformances() {
        List<PerformanceGetDto> performances = performanceService.findAllPerformances();
        return ResponseEntity.ok(performances);
    }

    /**
     * /dailband/boards/performances/{performance_id}
     * performanceId 매개변수로 받아서
     * 해당 공연 id에 대한 자세한 정보 넘겨준다.
     * @param performanceId
     * @return
     */
    @GetMapping("/dailband/boards/performances/{performance_id}")
    public ResponseEntity<PerformanceGetDto> getPerformanceById(@PathVariable("performance_id") Long performanceId) {
        Optional<PerformanceGetDto> performance = performanceService.findPerformanceById(performanceId);
        return performance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * /dailband/boards/performances
     * 작성한 것을 공연목록에 등록!! DTO
     * @param performancePostDto
     * @return
     */
    @PostMapping("/dailband/boards/performances")
    public ResponseEntity<PerformanceGetDto> savePerformance(@RequestBody PerformancePostDto performancePostDto) {
        PerformanceGetDto savedPerformance = performanceService.savePerformance(performancePostDto);
        return ResponseEntity.ok(savedPerformance);
    }

    /**
     * /dailband/boards/performances/{performance_id
     * 해당 공연을 삭제한다!
     * @param performanceId
     * @return
     */
    @DeleteMapping("/dailband/boards/performances/{performance_id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable("performance_id") Long performanceId) {
        performanceService.deletePerformance(performanceId);
        return ResponseEntity.noContent().build();
    }


}