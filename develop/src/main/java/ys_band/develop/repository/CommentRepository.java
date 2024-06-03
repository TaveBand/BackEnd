package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
