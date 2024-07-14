package ys_band.develop.dto.post;

import ys_band.develop.domain.Post;

public class PostDtoConverter {

    public static PostGetDto toPostGetDto(Post post) {
        PostGetDto dto = new PostGetDto();
        dto.setPost_id(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUser().getUserId());
        // 추가 필드들 변환
        return dto;
    }
}