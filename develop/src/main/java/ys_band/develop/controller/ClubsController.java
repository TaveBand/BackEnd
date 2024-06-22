package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.jaehyun.PostDTO;
import ys_band.develop.service.ClubsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ClubsController는 동아리 모집 게시물 관련 작업을 처리합니다.
 * 여기에는 게시물 작성, 조회, 수정, 삭제 기능이 포함됩니다.
 */
@RestController
@RequestMapping("/dailband/boards/clubs")
public class ClubsController {

    private final ClubsService clubsService;

    /**
     * ClubsController의 생성자입니다.
     *
     * @param clubsService 동아리 모집 게시물 관련 작업을 처리하는 서비스
     */
    @Autowired
    public ClubsController(ClubsService clubsService) {
        this.clubsService = clubsService;
    }

    /**
     * 동아리 모집 게시물을 작성합니다.
     *
     * @param postDTO 게시물 정보를 담은 DTO
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 게시물 ID와 성공 메시지를 담은 ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Long postId = clubsService.createPost(postDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post created successfully");
        response.put("post_id", postId);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * 모든 동아리 모집 게시물을 조회합니다.
     *
     * @return 모든 게시물 정보를 담은 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = clubsService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * 특정 동아리 모집 게시물을 조회합니다.
     *
     * @param postId 조회할 게시물의 ID
     * @return 조회한 게시물 정보를 담은 ResponseEntity
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO post = clubsService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    /**
     * 특정 동아리 모집 게시물을 수정합니다.
     *
     * @param postId 수정할 게시물의 ID
     * @param postDTO 수정할 게시물 정보를 담은 DTO
     * @param userDetails 인증된 사용자 정보
     * @return 수정된 게시물 ID와 성공 메시지를 담은 ResponseEntity
     */
    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        PostDTO updatedPost = clubsService.updatePost(postId, postDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post updated successfully");
        response.put("post_id", updatedPost.getPost_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 동아리 모집 게시물을 삭제합니다.
     *
     * @param postId 삭제할 게시물의 ID
     * @param userDetails 인증된 사용자 정보
     * @return 삭제 성공 메시지를 담은 ResponseEntity
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        clubsService.deletePost(postId, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post deleted successfully");
        return ResponseEntity.ok(response);
    }
}