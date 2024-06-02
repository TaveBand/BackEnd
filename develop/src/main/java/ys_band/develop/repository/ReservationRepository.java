package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.Reservation;
import ys_band.develop.domain.User;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPerformance(Performance performance);
    List<Reservation> findByUser(User user);
}