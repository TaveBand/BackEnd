package ys_band.develop.dto.youtube;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
@JsonNaming(
        PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class YoutubeGetDTO {
    private String title;
    private String link;
    private Long userId;
}
