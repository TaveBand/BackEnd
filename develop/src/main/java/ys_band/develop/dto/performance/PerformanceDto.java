package ys_band.develop.dto.performance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceDto {
    private Long performanceId;
    private String title;
    private java.sql.Date date;
    private java.sql.Time time;
    private String venue;
    private int totalSeats;
    private int currentSeats;
}