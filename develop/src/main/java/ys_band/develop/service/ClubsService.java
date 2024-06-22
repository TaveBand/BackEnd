package ys_band.develop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys_band.develop.domain.Board;
import ys_band.develop.domain.File;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.jaehyun.FileDTO;
import ys_band.develop.dto.jaehyun.PostDTO;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static ys_band.develop.dto.jaehyun.PostDTO.fromEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubsService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    //동아리모집게시판 게시물 작성
    @Transactional
    public Long createPost(PostDTO postDTO, UserDetails userDetails) {
        log.info("Entering createPost in ClubsService with PostDTO: {}", postDTO);

        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));
        Board board = boardRepository.findByName("clubs")
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(currentUser);
        post.setBoard(board);

        post = postRepository.save(post);
        if (postDTO.getFileUrl() != null) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFile_url(postDTO.getFileUrl());
            File savedFile = saveFile(fileDTO);
            post.setFile(savedFile);
        }

        log.info("Successfully created post in ClubsService");
        return post.getPostId();
    }

    private File saveFile(FileDTO fileDTO) {
        File file = fileDTO.toEntity();
        return fileRepository.save(file);
    }

    //동아리모집게시판 전체 게시물 조회
    public List<PostDTO> getAllPosts() {
        Board board = boardRepository.findByName("clubs")
                .orElseThrow(() -> new UserException("게시판을 찾을 수 없습니다."));
        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(fromEntity(post));
        }
        return postDTOs;
    }

    //동아리모집게시판 게시물 조회
    public PostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException("게시물을 찾을 수 없습니다"));
        return fromEntity(post);
    }

    //동아리모집게시판 게시물 수정
    @Transactional
    public PostDTO updatePost(Long postId, PostDTO postDTO, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        if (postDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(postDTO.getFileUrl());
            file.setFile_type(determineFileType(postDTO.getFileUrl()));
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post updatedPost = postRepository.save(post);
        return fromEntity(updatedPost);
    }

    //동아리모집게시판 게시물 삭제
    @Transactional
    public void deletePost(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        postRepository.deleteById(postId);
    }

    private String determineFileType(String fileUrl) {
        String fileType = "unknown";
        if (fileUrl != null && fileUrl.contains(".")) {
            String extension = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();
            switch (extension) {
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                    fileType = "image";
                    break;
                case "mp4":
                case "avi":
                case "mov":
                case "wmv":
                    fileType = "video";
                    break;
            }
        }
        return fileType;
    }
}