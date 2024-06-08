package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.mypr.MyPrGetDTO;
import ys_band.develop.dto.mypr.MyPrPostDTO;
import ys_band.develop.service.MyPrService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards/pr")
public class MyPrPostController {

    private final MyPrService myPrService;

    @Autowired
    public MyPrPostController(MyPrService myPrService) {
        this.myPrService = myPrService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMyPrPost(@RequestBody MyPrGetDTO myPrGetDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Long postId = myPrService.createMyPrPost(myPrGetDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "created!");
        response.put("post_id", postId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMyPrPosts() {
        List<MyPrPostDTO> posts = myPrService.getAllMyPrPosts();
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<MyPrPostDTO> getMyPrPost(@PathVariable Long postId) {
        MyPrPostDTO post = myPrService.getMyPrPost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updateMyPrPost(@PathVariable Long postId, @RequestBody MyPrGetDTO myPrGetDTO, @AuthenticationPrincipal UserDetails userDetails) {
        MyPrPostDTO post = myPrService.updateMyPrPost(postId, myPrGetDTO, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post updated successfully");
        response.put("post_id", post.getPost_id());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> deleteMyPrPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        myPrService.deleteMyPrPost(postId, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Post deleted successfully");
        return ResponseEntity.ok(response);
    }
}
