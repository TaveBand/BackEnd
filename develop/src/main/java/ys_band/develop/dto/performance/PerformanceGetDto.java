package ys_band.develop.dto.performance;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceGetDto {
    private Long performanceId;
    private String title;
    private String content; // 추가된 속성
    private java.sql.Date date;
    private java.sql.Time time;
    private String venue;
    private int totalSeats;
    private int currentSeats;
    private String imagePath; // 추가된 속성
}