package ys_band.develop.dto.performance;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PerformancePostDto {
    private String title;
    private String content; // 추가된 속성
    private java.sql.Date date;
    private java.sql.Time time;
    private String venue;
    private int totalSeats;
    private String imagePath; // 추가된 속성
    private Long userId;
}