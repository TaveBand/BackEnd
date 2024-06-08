package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.unionperformance.UnionPerformanceGetDTO;
import ys_band.develop.dto.unionperformance.UnionPerformancePostDTO;
import ys_band.develop.service.UnionPerformanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards/union-performances")
public class UnionPerformancePostController {

    private final UnionPerformanceService unionPerformanceService;

    @Autowired
    public UnionPerformancePostController(UnionPerformanceService unionPerformanceService) {
        this.unionPerformanceService = unionPerformanceService;
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPerformancePost(@RequestBody UnionPerformanceGetDTO unionPerformanceGetDTO, @AuthenticationPrincipal UserDetails userDetails){
        Long postId = unionPerformanceService.createPerformancePost(unionPerformanceGetDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post created successfully");
        response.put("post_id", postId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPerformancePosts() {
        List<UnionPerformancePostDTO> posts = unionPerformanceService.getAllUnionPerformancePosts();
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
//      response.put("currentPage", page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<UnionPerformancePostDTO> getPerformancePost(@PathVariable Long postId) {
        UnionPerformancePostDTO post = unionPerformanceService.getUnionPerformancePost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updatePerformancePost(@PathVariable Long postId, @RequestBody UnionPerformanceGetDTO unionPerformanceGetDTO, @AuthenticationPrincipal UserDetails userDetails) {
        UnionPerformancePostDTO post = unionPerformanceService.updateUnionPerformancePost(postId, unionPerformanceGetDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post updated successfully");
        response.put("post_id", post.getPostId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> deletePerformancePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        unionPerformanceService.deleteUnionPerformancePost(postId, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post deleted successfully");
        return ResponseEntity.ok(response);
    }
}
