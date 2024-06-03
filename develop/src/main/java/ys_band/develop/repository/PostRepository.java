package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByBoardBoardId(Long board_id);

}
