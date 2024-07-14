package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.comment.CommentGetDTO;
import ys_band.develop.dto.comment.CommentPostDTO;
import ys_band.develop.service.Post.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentGetDTO commentRequest, @AuthenticationPrincipal UserDetails userDetails) {
        Long commentId = commentService.createComment(commentRequest, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comment created successfully");
        response.put("comment_id", commentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentPostDTO>> getAllComments(@RequestParam Long postId) {
        List<CommentPostDTO> comments = commentService.getAllComments(postId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long commentId, @RequestBody CommentGetDTO commentRequest, @AuthenticationPrincipal UserDetails userDetails) {
        CommentPostDTO comment = commentService.updateComment(commentId, commentRequest, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comment updated successfully");
        response.put("comment_id", comment.getCommentId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comment deleted successfully");
        return ResponseEntity.ok(response);
    }
}