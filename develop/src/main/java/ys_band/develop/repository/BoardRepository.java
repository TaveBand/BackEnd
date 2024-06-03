package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByName(String name);
}
