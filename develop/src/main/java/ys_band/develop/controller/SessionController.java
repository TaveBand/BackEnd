package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.session.SessionGetDTO;
import ys_band.develop.dto.session.SessionPostDTO;
import ys_band.develop.dto.session.SessionPostDTOWithoutComments;
import ys_band.develop.dto.youtube.YoutubeGetDTO;
import ys_band.develop.service.SessionService;
import ys_band.develop.service.YoutubeLinkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private YoutubeLinkService youtubeLinkService;

    @GetMapping("/{board_id}")
    public ResponseEntity<Map<String, Object>> getPostsByBoardId(@PathVariable("board_id") Long boardId) {
        List<SessionPostDTOWithoutComments> posts = sessionService.getAllSessionPosts(boardId);
        List<YoutubeGetDTO> youtubes = youtubeLinkService.getAllYoutubeLinksByBoard(boardId);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("youtubes", youtubes);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
