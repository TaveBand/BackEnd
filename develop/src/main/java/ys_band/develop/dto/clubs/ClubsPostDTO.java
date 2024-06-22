package ys_band.develop.dto.clubs;


import lombok.EqualsAndHashCode;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.Session;

import java.time.LocalDateTime;
import java.util.List;

//@EqualsAndHashCode(callSuper = true)
public class ClubsPostDTO{
    private Long post_id;
    private String title;
    private String content;
    private String fileUrl;
    private String nickname;
    private String email;
    private List<Session> sessions;
    private List<Comment> comments;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
