package ys_band.develop.dto.reservation;

import ys_band.develop.domain.Performance;
import ys_band.develop.domain.Reservation;
import ys_band.develop.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationDtoConverter {

    public static ReservationDto toReservationDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setReservationId(reservation.getReservation_id());
        dto.setPerformanceId(reservation.getPerformance().getPerformance_id());
        dto.setUserId(reservation.getUser().getUserId());
        dto.setReservationDate(reservation.getReservation_date());
        return dto;
    }

    public static Reservation toReservationEntity(ReservationDto dto, Performance performance, User user) {
        Reservation reservation = new Reservation();
        reservation.setPerformance(performance);
        reservation.setUser(user);
        reservation.setReservation_date(dto.getReservationDate());
        return reservation;
    }

    public static List<ReservationDto> toReservationDtoList(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDtoConverter::toReservationDto).collect(Collectors.toList());
    }
}