package ys_band.develop.dto.jaehyun;

import lombok.*;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.Post;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO{

    private Long post_id;


    @NonNull
    private String title;
    @NonNull
    private String content;

    private String fileUrl;
    private List<Comment> comments;

    public static PostDTO fromEntity(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setPost_id(post.getPostId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());

        if (post.getFile() != null) {
            postDTO.setFileUrl(post.getFile().getFile_url());
        } else {
            postDTO.setFileUrl(null);
        }

        return postDTO;
    }
    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }


}
