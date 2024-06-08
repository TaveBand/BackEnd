package ys_band.develop.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentGetDTO {
    @JsonProperty("post_id")
    private Long postId;
    private String content;
}
