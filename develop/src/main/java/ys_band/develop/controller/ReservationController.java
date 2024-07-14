package ys_band.develop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.reservation.ReservationDto;
import ys_band.develop.service.Post.ReservationService;

import java.util.List;


@RestController
public class ReservationController {


    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    /**
     * 특정 공연을 예약합니다.
     *
     * @param performanceId 공연 ID
     * @param reservationDto 예약 정보 DTO
     * @return 예약된 정보 DTO
     */
    @PostMapping("/dailband/performances/reservation/{performanceId}")
    public ResponseEntity<ReservationDto> reservePerformance(@PathVariable Long performanceId,
                                                             @RequestBody ReservationDto reservationDto) {
        reservationDto.setPerformanceId(performanceId);
        ReservationDto savedReservation = reservationService.saveReservation(reservationDto);
        return ResponseEntity.ok(savedReservation);
    }

    /**
     * 없어도 되는 듯함? mypage 컨트롤러에서 구현?
     * 현재 세션의 사용자 ID로 해당 사용자가 소유한 예약들을 조회합니다.
     * @param session 현재 HTTP 세션
     * @return 해당 사용자가 소유한 예약 목록
     */
    @GetMapping("/dailband/user/myreservations")
    public ResponseEntity<List<ReservationDto>> getMyReservations(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        List<ReservationDto> reservations = reservationService.findReservationsByUserId(userId);
        return ResponseEntity.ok(reservations); // ok?
    }
}