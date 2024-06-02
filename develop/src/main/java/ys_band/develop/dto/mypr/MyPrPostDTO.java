package ys_band.develop.dto.mypr;

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
    private String fileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
    private String email;
    private List<Session> sessions;
    private List<Comment> comments;
}
