package ys_band.develop.dto;


import lombok.*;
import ys_band.develop.domain.BaseTime;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.Post;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PostDTO extends BaseTime {

    private Long postId;


    @NonNull
    private String title;
    @NonNull
    private String content;

    private String fileUrl;
    private List<Comment> comments;

    public static PostDTO fromEntity(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(post.getPostId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());

        if (post.getFile() != null) {
            postDTO.setFileUrl(post.getFile().getFile_url());
        } else {
            postDTO.setFileUrl(null);
        }

        return postDTO;
    }


}


