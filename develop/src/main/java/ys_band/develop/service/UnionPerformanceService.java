package ys_band.develop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Board;
import ys_band.develop.domain.File;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.unionperformance.UnionPerformanceGetDTO;
import ys_band.develop.dto.unionperformance.UnionPerformancePostDTO;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnionPerformanceService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public UnionPerformanceService(PostRepository postRepository, UserRepository userRepository, FileRepository fileRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.boardRepository = boardRepository;
    }

    public Long createPerformancePost(UnionPerformanceGetDTO unionPerformanceGetDTO, UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));
        Board board = boardRepository.findByName("union-performances")
                .orElseThrow(() -> new RuntimeException("Board not found"));
        Post post = new Post();
        post.setTitle(unionPerformanceGetDTO.getTitle());
        post.setContent(unionPerformanceGetDTO.getContent());
        post.setUser(currentUser);
        post.setBoard(board);

        if (unionPerformanceGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(unionPerformanceGetDTO.getFileUrl());
            file.setFile_type(determineFileType(unionPerformanceGetDTO.getFileUrl()));
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post savedPost = postRepository.save(post);

        return savedPost.getPost_id();
    }

    public List<UnionPerformancePostDTO> getAllUnionPerformancePosts() {

        Board board = boardRepository.findByName("union-performances")
                .orElseThrow(() -> new RuntimeException("Board not found"));



        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<UnionPerformancePostDTO> unionPerformancePostDTOS = new ArrayList<>();
        for (Post post : posts) {
            unionPerformancePostDTOS.add(transformDTO(post));
        }

        return unionPerformancePostDTOS;
    }

    public UnionPerformancePostDTO getUnionPerformancePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));
        return transformDTO(post);
    }

    public UnionPerformancePostDTO updateUnionPerformancePost(Long postId, UnionPerformanceGetDTO unionPerformanceGetDTO, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not Authorized");
        }

        post.setTitle(unionPerformanceGetDTO.getTitle());
        post.setContent(unionPerformanceGetDTO.getContent());

        if (unionPerformanceGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(unionPerformanceGetDTO.getFileUrl());
            file.setFile_type(determineFileType(unionPerformanceGetDTO.getFileUrl()));
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post updatedPost = postRepository.save(post);
        return transformDTO(updatedPost);
    }

    public void deleteUnionPerformancePost(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not Authorized");
        }

        postRepository.deleteById(postId);
    }

    private UnionPerformancePostDTO transformDTO(Post post) {
        UnionPerformancePostDTO unionPerformancePostDTO = new UnionPerformancePostDTO();
        unionPerformancePostDTO.setPostId(post.getPost_id());
        unionPerformancePostDTO.setTitle(post.getTitle());
        unionPerformancePostDTO.setContent(post.getContent());

        if (post.getFile() != null) {
            unionPerformancePostDTO.setFileUrl(post.getFile().getFile_url());
        }

        unionPerformancePostDTO.setCreatedAt(post.getCreated_at());
        unionPerformancePostDTO.setModifiedAt(post.getModified_at());
        unionPerformancePostDTO.setNickname(post.getUser().getNickname());
        unionPerformancePostDTO.setComments(post.getComments());
        return unionPerformancePostDTO;
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
