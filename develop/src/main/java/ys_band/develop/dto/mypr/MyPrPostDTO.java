package ys_band.develop.dto.mypr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.Session;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MyPrPostDTO {
    private Long post_id;
    private String title;
    private String content;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("create_at")
    private LocalDateTime createdAt;
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;
    private String nickname;
    private String email;
    private List<Session> sessions;
    private List<Comment> comments;
}
