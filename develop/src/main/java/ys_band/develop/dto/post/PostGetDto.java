package ys_band.develop.dto.post;

import lombok.Data;

@Data
public class PostGetDto {
    private Long post_id;
    private String title;
    private String content;
    private Long user_id;
    // 추가 필드들
}