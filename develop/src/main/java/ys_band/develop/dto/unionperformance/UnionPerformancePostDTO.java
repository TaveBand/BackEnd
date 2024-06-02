package ys_band.develop.dto.unionperformance;

import lombok.Getter;
import lombok.Setter;
import ys_band.develop.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UnionPerformancePostDTO {
    private Long postId;
    private String title;
    private String content;
    private String fileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
    private List<Comment> comments;
}