package ys_band.develop.dto.unionperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ys_band.develop.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UnionPerformancePostDTO {
    @JsonProperty("post_id")
    private Long postId;
    private String title;
    private String content;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;
    private String nickname;
    private List<Comment> comments;
}