package ys_band.develop.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonNaming
public class SessionPostDTOWithoutComments {
    @JsonProperty("post_id")
    private Long postId;
    private String title;
    private String content;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private String nickname;


}
