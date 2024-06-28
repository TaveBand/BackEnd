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
import ys_band.develop.dto.session.SessionGetDTO;
import ys_band.develop.dto.session.SessionPostDTO;
import ys_band.develop.dto.session.SessionPostDTOWithoutComments;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public SessionService(PostRepository postRepository, UserRepository userRepository, FileRepository fileRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.boardRepository = boardRepository;
    }

    public Long createSessionPost(SessionGetDTO sessionGetDTO, UserDetails userDetails, Long boardId) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        Post post = new Post();
        post.setTitle(sessionGetDTO.getTitle());
        post.setContent(sessionGetDTO.getContent());
        post.setUser(currentUser);
        post.setBoard(board);

        if (sessionGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(sessionGetDTO.getFileUrl());
            file.setFile_type(determineFileType(sessionGetDTO.getFileUrl()));
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

    public List<SessionPostDTOWithoutComments> getAllSessionPosts(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<SessionPostDTOWithoutComments> sessionPostDTOS = new ArrayList<>();
        for (Post post : posts) {
            sessionPostDTOS.add(convertToSessionPostDTOWithoutComments(post));
        }

        return sessionPostDTOS;
    }

    public SessionPostDTO getSessionPost(Long boardId, Long postId) {
        Post post = postRepository.findByPostIdAndBoardBoardId(postId, boardId)
                .orElseThrow(() -> new UserException("Post not found"));
        return convertToSessionPostDTO(post);
    }

    public SessionPostDTO updateSessionPost(Long boardId, Long postId, SessionGetDTO sessionGetDTO, UserDetails userDetails) {
        Post post = postRepository.findByPostIdAndBoardBoardId(postId, boardId)
                .orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not authorized to update this post");
        }

        post.setTitle(sessionGetDTO.getTitle());
        post.setContent(sessionGetDTO.getContent());

        if (sessionGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(sessionGetDTO.getFileUrl());
            file.setFile_type(determineFileType(sessionGetDTO.getFileUrl()));
            file.setPost(post);
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post updatedPost = postRepository.save(post);
        return convertToSessionPostDTO(updatedPost);
    }

    public void deleteSessionPost(Long boardId, Long postId, UserDetails userDetails) {
        Post post = postRepository.findByPostIdAndBoardBoardId(postId, boardId)
                .orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not authorized to delete this post");
        }

        postRepository.deleteById(postId);
    }

    private SessionPostDTO convertToSessionPostDTO(Post post) {
        SessionPostDTO sessionPostDTO = new SessionPostDTO();
        sessionPostDTO.setPostId(post.getPostId());
        sessionPostDTO.setTitle(post.getTitle());
        sessionPostDTO.setContent(post.getContent());
        if (post.getFile() != null) {
            sessionPostDTO.setFileUrl(post.getFile().getFile_url());
        }
        sessionPostDTO.setCreatedAt(post.getCreated_at());
        sessionPostDTO.setModifiedAt(post.getModified_at());
        sessionPostDTO.setNickname(post.getUser().getNickname());
        sessionPostDTO.setComments(post.getComments().stream()
                .map(this::convertToCommentPostDTO)
                .collect(Collectors.toList()));
        return sessionPostDTO;
    }

    private SessionPostDTOWithoutComments convertToSessionPostDTOWithoutComments(Post post) {
        SessionPostDTOWithoutComments sessionPostDTO = new SessionPostDTOWithoutComments();
        sessionPostDTO.setPostId(post.getPostId());
        sessionPostDTO.setTitle(post.getTitle());
        sessionPostDTO.setContent(post.getContent());
        if (post.getFile() != null) {
            sessionPostDTO.setFileUrl(post.getFile().getFile_url());
        }
        sessionPostDTO.setCreatedAt(post.getCreated_at());
        sessionPostDTO.setNickname(post.getUser().getNickname());
        return sessionPostDTO;
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

