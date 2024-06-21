package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.session.SessionGetDTO;
import ys_band.develop.dto.session.SessionPostDTO;
import ys_band.develop.service.SessionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{board_id}")
    public ResponseEntity<List<SessionPostDTO>> getPostsByBoardId(@PathVariable("board_id") Long boardId) {
        List<SessionPostDTO> posts = sessionService.getAllSessionPosts(boardId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{board_id}/{post_id}")
    public ResponseEntity<SessionPostDTO> getPostById(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId) {
        SessionPostDTO post = sessionService.getSessionPost(boardId, postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/{board_id}")
    public ResponseEntity<Map<String, Object>> createPost(@PathVariable("board_id") Long boardId,
                                                          @RequestBody SessionGetDTO sessionGetDTO,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Long postId = sessionService.createSessionPost(sessionGetDTO, userDetails, boardId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post created successfully");
        response.put("post_id", postId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{board_id}/{post_id}")
    public ResponseEntity<SessionPostDTO> updatePost(@PathVariable("board_id") Long boardId,
                                                     @PathVariable("post_id") Long postId,
                                                     @RequestBody SessionGetDTO sessionGetDTO,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        SessionPostDTO updatedPost = sessionService.updateSessionPost(boardId, postId, sessionGetDTO, userDetails);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{board_id}/{post_id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable("board_id") Long boardId,
                                                          @PathVariable("post_id") Long postId,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        sessionService.deleteSessionPost(boardId, postId, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
