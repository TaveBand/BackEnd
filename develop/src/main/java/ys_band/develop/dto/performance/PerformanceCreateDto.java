package ys_band.develop.dto.performance;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceCreateDto {
    private String title;
    private java.sql.Date date;
    private java.sql.Time time;
    private String venue;
    private int totalSeats;
    private Long userId;
}