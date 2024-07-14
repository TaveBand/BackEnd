package ys_band.develop.service.Post;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.Reservation;
import ys_band.develop.domain.User;
import ys_band.develop.dto.reservation.ReservationDto;
import ys_band.develop.dto.reservation.ReservationDtoConverter;
import ys_band.develop.repository.PerformanceRepository;
import ys_band.develop.repository.ReservationRepository;
import ys_band.develop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 모든 예약을 조회합니다.
     *
     * @return 예약 목록
     */
    public List<ReservationDto> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationDtoConverter.toReservationDtoList(reservations);
    }

    /**
     * 예약 ID로 특정 예약을 조회합니다.
     *
     * @param id 예약 ID
     * @return 예약 DTO (Optional)
     */
    public Optional<ReservationDto> findReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(ReservationDtoConverter::toReservationDto);
    }

    /**
     * 새로운 예약을 저장합니다.
     * 예약 시 공연의 current_seats 증가
     *
     * @param reservationDto 예약 DTO
     * @return 저장된 예약 DTO
     */
    public ReservationDto saveReservation(ReservationDto reservationDto) {
        Performance performance = performanceRepository.findById(reservationDto.getPerformanceId())
                .orElseThrow(() -> new IllegalArgumentException("Performance not found for id: " + reservationDto.getPerformanceId()));
        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + reservationDto.getUserId()));

        // 예약 시 공연의 current_seats 증가
        if (performance.getCurrent_seats() >= performance.getTotal_seats()) {
            throw new IllegalArgumentException("No more seats available for this performance");
        }
        performance.setCurrent_seats(performance.getCurrent_seats() + 1);
        performanceRepository.save(performance);

        Reservation reservation = ReservationDtoConverter.toReservationEntity(reservationDto, performance, user);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationDtoConverter.toReservationDto(savedReservation);
    }

    /**
     * 예약을 삭제합니다.
     * 예약 삭제 시 공연의 current_seats 감소
     *
     * @param id 예약 ID
     */
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found for id: " + id));

        Performance performance = reservation.getPerformance();
        performance.setCurrent_seats(performance.getCurrent_seats() - 1);
        performanceRepository.save(performance);

        reservationRepository.deleteById(id);
    }

    /**
     * 사용자 ID로 해당 사용자가 소유한 예약들을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자가 소유한 예약 목록
     */
    public List<ReservationDto> findReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return ReservationDtoConverter.toReservationDtoList(reservations);
    }
}