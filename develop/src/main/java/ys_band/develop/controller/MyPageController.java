package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.jaehyun.PostDTO;
import ys_band.develop.dto.post.PostDtoConverter;
import ys_band.develop.dto.post.PostGetDto;
import ys_band.develop.dto.user.UserDtoConverter;
import ys_band.develop.dto.user.UserGetDto;
import ys_band.develop.dto.user.UserPostDto;
import ys_band.develop.service.PostService;
import ys_band.develop.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ys_band.develop.dto.jaehyun.PostDTO.fromEntity;

/**
 * MyPageController는 사용자와 관련된 작업을 처리합니다.
 * 여기에는 사용자 정보 조회 및 업데이트, 사용자가 작성한 게시물 조회가 포함됩니다.
 */
@RestController
@RequestMapping("/dailband/user")
public class MyPageController {

    private final UserService userService;
    private final PostService postService;

    /**
     * MyPageController의 생성자입니다.
     *
     * @param userService 사용자 관련 작업을 처리하는 서비스
     * @param postService 게시물 관련 작업을 처리하는 서비스
     */
    @Autowired
    public MyPageController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    /**
     * 인증된 사용자의 정보를 조회합니다.
     *
     * @return 인증된 사용자의 정보를 담은 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<UserGetDto> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            UserGetDto userGetDto = UserDtoConverter.toUserGetDto(optionalUser.get());
            return ResponseEntity.ok(userGetDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 인증된 사용자의 정보를 업데이트합니다.
     *
     * @param userPostDto 업데이트할 사용자 정보를 담은 DTO
     * @return 업데이트된 사용자 정보를 담은 ResponseEntity
     */
    @PutMapping
    public ResponseEntity<UserGetDto> updateUserInfo(@RequestBody UserPostDto userPostDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userPostDto.getUsername());
            user.setPassword(userPostDto.getPassword()); // 비밀번호는 암호화 필요
            user.setEmail(userPostDto.getEmail());
            user.setNickname(userPostDto.getNickname());
            user.setSchoolId(userPostDto.getSchoolId());

            userService.save(userPostDto);

            UserGetDto updatedUserGetDto = UserDtoConverter.toUserGetDto(user);
            return ResponseEntity.ok(updatedUserGetDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 특정 사용자가 작성한 게시물을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자가 작성한 게시물 목록을 담은 ResponseEntity
     */
    @GetMapping("/{user_id}/posts")
    public ResponseEntity<List<PostGetDto>> getUserPosts(@PathVariable("user_id") Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        List<PostGetDto> postGetDtos = posts.stream()
                .map(PostDtoConverter::toPostGetDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postGetDtos);
    }

    /**
     * 특정 사용자가 스크랩한 게시물을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자가 스크랩한 게시물 목록을 담은 ResponseEntity
     */
    /*
    @GetMapping("/{user_id}/scraps")
    public ResponseEntity<List<PostDTO>> getMyScrappedPosts(@PathVariable String user_id) {
        return ResponseEntity.ok(userService.getMyScrappedPosts(user_id));
    }
    */
}