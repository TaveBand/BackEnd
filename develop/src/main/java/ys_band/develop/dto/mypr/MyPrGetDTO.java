package ys_band.develop.dto.mypr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPrGetDTO {
    private String title;
    private String content;
    @JsonProperty("file_url")
    private String fileUrl;
}
