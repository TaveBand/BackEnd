package ys_band.develop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Board;
import ys_band.develop.domain.Comment;
import ys_band.develop.domain.File;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;
import ys_band.develop.dto.comment.CommentPostDTO;
import ys_band.develop.dto.unionperformance.UnionPerformanceGetDTO;
import ys_band.develop.dto.unionperformance.UnionPerformancePostDTO;
import ys_band.develop.dto.unionperformance.UnionPerformancePostDTOWithoutComments;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            // Post 저장
            Post savedPost = postRepository.save(post);
            file.setPost(savedPost);
            // File 저장
            File savedFile = fileRepository.save(file);
            savedPost.setFile(savedFile);
            postRepository.save(savedPost);
        } else {
            postRepository.save(post);
        }

        return post.getPostId();
    }

    public List<UnionPerformancePostDTOWithoutComments> getAllUnionPerformancePosts() {
        Board board = boardRepository.findByName("union-performances")
                .orElseThrow(() -> new RuntimeException("Board not found"));
        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<UnionPerformancePostDTOWithoutComments> unionPerformancePostDTOs = new ArrayList<>();
        for (Post post : posts) {
            unionPerformancePostDTOs.add(convertToUnionPerformancePostDTOWithoutComments(post));
        }

        return unionPerformancePostDTOs;
    }

    public UnionPerformancePostDTO getUnionPerformancePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));
        return convertToUnionPerformancePostDTO(post);
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
            file.setPost(post);
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post updatedPost = postRepository.save(post);
        return convertToUnionPerformancePostDTO(updatedPost);
    }

    public void deleteUnionPerformancePost(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not Authorized");
        }

        postRepository.deleteById(postId);
    }

    private UnionPerformancePostDTO convertToUnionPerformancePostDTO(Post post) {
        UnionPerformancePostDTO unionPerformancePostDTO = new UnionPerformancePostDTO();
        unionPerformancePostDTO.setPostId(post.getPostId());
        unionPerformancePostDTO.setTitle(post.getTitle());
        unionPerformancePostDTO.setContent(post.getContent());
        if (post.getFile() != null) {
            unionPerformancePostDTO.setFileUrl(post.getFile().getFile_url());
        }
        unionPerformancePostDTO.setCreatedAt(post.getCreated_at());
        unionPerformancePostDTO.setModifiedAt(post.getModified_at());
        unionPerformancePostDTO.setNickname(post.getUser().getNickname());
        unionPerformancePostDTO.setComments(post.getComments().stream()
                .map(this::convertToCommentPostDTO)
                .collect(Collectors.toList()));
        return unionPerformancePostDTO;
    }

    private UnionPerformancePostDTOWithoutComments convertToUnionPerformancePostDTOWithoutComments(Post post) {
        UnionPerformancePostDTOWithoutComments unionPerformancePostDTO = new UnionPerformancePostDTOWithoutComments();
        unionPerformancePostDTO.setPostId(post.getPostId());
        unionPerformancePostDTO.setTitle(post.getTitle());
        unionPerformancePostDTO.setContent(post.getContent());
        if (post.getFile() != null) {
            unionPerformancePostDTO.setFileUrl(post.getFile().getFile_url());
        }
        unionPerformancePostDTO.setCreatedAt(post.getCreated_at());
        unionPerformancePostDTO.setNickname(post.getUser().getNickname());
        return unionPerformancePostDTO;
    }

    private CommentPostDTO convertToCommentPostDTO(Comment comment) {
        CommentPostDTO commentPostDTO = new CommentPostDTO();
        commentPostDTO.setCommentId(comment.getComment_id());
        commentPostDTO.setContent(comment.getContent());
        commentPostDTO.setCreatedAt(comment.getCreated_at());
        commentPostDTO.setModifiedAt(comment.getModified_at());
        commentPostDTO.setNickname(comment.getUser().getNickname());
        return commentPostDTO;
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
