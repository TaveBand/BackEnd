package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.reservation.ReservationDto;
import ys_band.develop.service.ReservationService;

@RestController
@RequestMapping("/dailband/performances/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 특정 공연을 예약합니다.
     *
     * @param performanceId 공연 ID
     * @param reservationDto 예약 정보 DTO
     * @return 예약된 정보 DTO
     */
    @PostMapping("/{performanceId}")
    public ResponseEntity<ReservationDto> reservePerformance(@PathVariable Long performanceId,
                                                             @RequestBody ReservationDto reservationDto) {
        reservationDto.setPerformanceId(performanceId);
        ReservationDto savedReservation = reservationService.saveReservation(reservationDto);
        return ResponseEntity.ok(savedReservation);
    }
}