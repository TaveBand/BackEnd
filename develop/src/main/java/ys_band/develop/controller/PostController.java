package ys_band.develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.post.PostDTO;
import ys_band.develop.service.Post.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards")
@RequiredArgsConstructor
public class PostController {
    //clubs, matching
    private final PostService postService;

    //게시물 생성
    @PostMapping("/{boardName}")
    public ResponseEntity<Map<String, Object>> createPost(@PathVariable String boardName, @RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails){
        try {
            Long postId = postService.createPost(postDTO, userDetails, boardName);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("postId", postId, "message", "Post created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{boardName}")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable String boardName) {
        try {
            List<PostDTO> posts = postService.getAllPosts(boardName);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{boardName}/{postId}")
    public ResponseEntity<?> getPost(@PathVariable String boardName, @PathVariable Long postId) {
        try {
            PostDTO post = postService.getPost(postId);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{boardName}/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable String boardName, @PathVariable Long postId, @RequestBody PostDTO postDTO, UserDetails userDetails) {
        try {
            PostDTO updatedPost = postService.updatePost(postId, postDTO, userDetails);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{boardName}/{postId}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable String boardName, @PathVariable Long postId, UserDetails userDetails) {
        try {
            postService.deletePost(postId, userDetails);
            return ResponseEntity.ok(Map.of("message", "Post deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
