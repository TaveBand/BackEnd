package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByPostIdAndBoardBoardId(Long postId, Long boardId);
    List<Post> findAllByBoardBoardId(Long boardId);

    List<Post> findAllByUserUserId(Long userId);

}
