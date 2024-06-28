package ys_band.develop.dto.mypr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ys_band.develop.domain.Session;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MyPrPostDTOWithoutComments {
    @JsonProperty("post_id")
    private Long postId;
    private String title;
    private String content;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private String nickname;
    private String email;
    private List<Session> sessions;
}
