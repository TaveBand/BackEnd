package ys_band.develop.controller;


import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.domain.User;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.dto.post.PostDTO;
import ys_band.develop.dto.user.*;
import ys_band.develop.service.Post.PerformanceService;
import ys_band.develop.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dailband/user")
public class UserController {

    private final UserService userService;

    private final PerformanceService performanceService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, PerformanceService performanceService) {
        this.userService = userService;
        this.performanceService = performanceService;
    }

    //사용자 개인정보 조회
    @GetMapping("/profile")
    public ResponseEntity<UserGetDto> getMyUserInfo(Principal principal) throws Exception {
        String username = principal.getName();
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    //사용자 개인정보 수정
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserPostDto userPostDto, Principal principal) {
        try {
            String username = principal.getName();
            System.out.println(username);
            logger.debug("Updating profile for username: {}", username);
            if (userPostDto.getSessionIds() == null) {
                userPostDto.setSessionIds(new ArrayList<>());
            }
            System.out.println("수신된 세션 ID 리스트: " + userPostDto.getSessionIds());
            UserGetDto updatedUserDto = userService.updateUserProfile(username, userPostDto);
            return ResponseEntity.ok(updatedUserDto);
        } catch (Exception e) {
            logger.error("An error occurred while updating profile", e);
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    //관리자 권한으로 사용자 정보 조회

    @GetMapping("/{username}/profile")
    @PreAuthorize("hasAnyRole('ADMIN')")//'ADMIN" 생성 여부 확인
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByAdmin(username).get());
    }

    // 사용자가 작성한 게시물 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getMyPosts(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long userId = userService.getUserIdByUsername(username);
        List<PostDTO> posts = userService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
    //사용자가 작성한 공연정보 조회
    @GetMapping("/myperformances")
    public ResponseEntity<List<PerformanceGetDto>> getMyPerformancesPost(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long userId = userService.getUserIdByUsername(username);
        List<PerformanceGetDto> performances = performanceService.findPerformancesByUserId(userId);
        return ResponseEntity.ok(performances);
    }


}

    // 사용자가 스크랩한 게시물 조회
    /*
    @GetMapping("/{userId}/scraps")
    public ResponseEntity<List<PostDTO>> getMyScrappedPosts(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getMyScrappedPosts(userId));
    }
    */

