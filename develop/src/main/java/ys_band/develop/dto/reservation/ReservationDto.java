package ys_band.develop.dto.reservation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationDto {

    private Long reservationId;
    private Long performanceId;
    private Long userId;
    private java.sql.Date reservationDate;
}