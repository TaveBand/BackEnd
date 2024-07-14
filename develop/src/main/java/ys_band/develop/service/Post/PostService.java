package ys_band.develop.service.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys_band.develop.domain.Board;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.post.PostDTO;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static ys_band.develop.dto.post.PostDTO.fromEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    //동아리모집게시판,  연합동아리모집게시판
    @Transactional
    public Long createPost(PostDTO postDTO, UserDetails userDetails, String boardName) {
        log.info("Entering createPost with PostDTO: {}", postDTO);

        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));
        Board board = boardRepository.findByName(boardName)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(currentUser);
        post.setBoard(board);

        post = postRepository.save(post);

        log.info("Successfully created post in ClubsService");
        return post.getPostId();
    }



    //게시판 전체 게시물 조회
    public List<PostDTO> getAllPosts(String boardName) {
        Board board = boardRepository.findByName(boardName)
                .orElseThrow(() -> new UserException("게시판을 찾을 수 없습니다."));
        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(fromEntity(post));
        }
        return postDTOs;
    }

    //게시물 조회
    public PostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException("게시물을 찾을 수 없습니다"));
        return fromEntity(post);
    }

    //게시물 수정
    @Transactional
    public PostDTO updatePost(Long postId, PostDTO postDTO, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());


        Post updatedPost = postRepository.save(post);
        return fromEntity(updatedPost);
    }

    //게시물 삭제
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
