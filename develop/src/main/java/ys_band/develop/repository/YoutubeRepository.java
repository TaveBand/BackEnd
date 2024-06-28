package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ys_band.develop.domain.Youtube;

public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
}
