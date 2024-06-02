package ys_band.develop.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentPostDTO {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
}
