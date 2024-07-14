package ys_band.develop.service.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.comment.CommentGetDTO;
import ys_band.develop.dto.comment.CommentPostDTO;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.CommentRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Long createComment(CommentGetDTO commentRequest, UserDetails userDetails) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new UserException("Post not found"));

        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setPost(post);
        comment.setUser(currentUser);

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getComment_id();
    }

    public List<CommentPostDTO> getAllComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));
        List<Comment> comments = post.getComments();

        List<CommentPostDTO> commentPostDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentPostDTOS.add(convertToDTO(comment));
        }

        return commentPostDTOS;
    }

    public CommentPostDTO updateComment(Long commentId, CommentGetDTO commentRequest, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new UserException("Comment not found"));

        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("You are not authorized to update this comment");
        }

        comment.setContent(commentRequest.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    public void deleteComment(Long commentId, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new UserException("Comment not found"));

        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("You are not authorized to delete this comment");
        }

        commentRepository.deleteById(commentId);
    }

    private CommentPostDTO convertToDTO(Comment comment) {
        CommentPostDTO commentPostDTO = new CommentPostDTO();
        commentPostDTO.setCommentId(comment.getComment_id());
        commentPostDTO.setContent(comment.getContent());
        commentPostDTO.setCreatedAt(comment.getCreated_at());
        commentPostDTO.setModifiedAt(comment.getModified_at());
        commentPostDTO.setNickname(comment.getUser().getNickname());
        return commentPostDTO;
    }
}
