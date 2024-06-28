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
import ys_band.develop.dto.mypr.MyPrGetDTO;
import ys_band.develop.dto.mypr.MyPrPostDTO;
import ys_band.develop.dto.mypr.MyPrPostDTOWithoutComments;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.FileRepository;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPrService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public MyPrService(PostRepository postRepository, UserRepository userRepository, FileRepository fileRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.boardRepository = boardRepository;
    }

    public Long createMyPrPost(MyPrGetDTO myPrGetDTO, UserDetails userDetails) {
        if (userDetails == null) {
            throw new UserException("User not authenticated");
        }
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException("User not found"));
        Board board = boardRepository.findByName("pr")
                .orElseThrow(() -> new RuntimeException("Board not found"));
        Post post = new Post();
        post.setTitle(myPrGetDTO.getTitle());
        post.setContent(myPrGetDTO.getContent());
        post.setUser(currentUser);
        post.setBoard(board);

        if (myPrGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(myPrGetDTO.getFileUrl());
            file.setFile_type(determineFileType(myPrGetDTO.getFileUrl()));
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

    public List<MyPrPostDTOWithoutComments> getAllMyPrPosts() {
        Board board = boardRepository.findByName("pr")
                .orElseThrow(() -> new RuntimeException("Board not found"));
        List<Post> posts = postRepository.findAllByBoardBoardId(board.getBoardId());

        List<MyPrPostDTOWithoutComments> myPrPostDTOs = new ArrayList<>();
        for (Post post : posts) {
            myPrPostDTOs.add(convertToMyPrPostDTOWithoutComments(post));
        }

        return myPrPostDTOs;
    }

    public MyPrPostDTO getMyPrPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));
        return convertToMyPrPostDTO(post);
    }

    public MyPrPostDTO updateMyPrPost(Long postId, MyPrGetDTO myPrGetDTO, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not Authorized");
        }

        post.setTitle(myPrGetDTO.getTitle());
        post.setContent(myPrGetDTO.getContent());

        if (myPrGetDTO.getFileUrl() != null) {
            File file = new File();
            file.setFile_url(myPrGetDTO.getFileUrl());
            file.setFile_type(determineFileType(myPrGetDTO.getFileUrl()));
            file.setPost(post);
            File savedFile = fileRepository.save(file);
            post.setFile(savedFile);
        }

        Post updatedPost = postRepository.save(post);
        return convertToMyPrPostDTO(updatedPost);
    }

    public void deleteMyPrPost(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UserException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserException("Not Authorized");
        }

        postRepository.deleteById(postId);
    }

    private MyPrPostDTO convertToMyPrPostDTO(Post post) {
        MyPrPostDTO myPrPostDTO = new MyPrPostDTO();
        myPrPostDTO.setPost_id(post.getPostId());
        myPrPostDTO.setTitle(post.getTitle());
        myPrPostDTO.setContent(post.getContent());

        if (post.getFile() != null) {
            myPrPostDTO.setFileUrl(post.getFile().getFile_url());
        }
        myPrPostDTO.setCreatedAt(post.getCreated_at());
        myPrPostDTO.setModifiedAt(post.getModified_at());
        myPrPostDTO.setNickname(post.getUser().getNickname());
        myPrPostDTO.setEmail(post.getUser().getEmail());
        myPrPostDTO.setComments(post.getComments().stream()
                .map(this::convertToCommentPostDTO)
                .collect(Collectors.toList()));
        myPrPostDTO.setSessions(post.getUser().getSessions());

        return myPrPostDTO;
    }

    private MyPrPostDTOWithoutComments convertToMyPrPostDTOWithoutComments(Post post) {
        MyPrPostDTOWithoutComments myPrPostDTO = new MyPrPostDTOWithoutComments();
        myPrPostDTO.setPostId(post.getPostId());
        myPrPostDTO.setTitle(post.getTitle());
        myPrPostDTO.setContent(post.getContent());

        if (post.getFile() != null) {
            myPrPostDTO.setFileUrl(post.getFile().getFile_url());
        }
        myPrPostDTO.setCreatedAt(post.getCreated_at());
        myPrPostDTO.setNickname(post.getUser().getNickname());
        myPrPostDTO.setEmail(post.getUser().getEmail());
        myPrPostDTO.setSessions(post.getUser().getSessions());

        return myPrPostDTO;
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
