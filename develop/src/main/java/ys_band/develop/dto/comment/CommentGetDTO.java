package ys_band.develop.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentGetDTO {
    private Long postId;
    private String content;
}
