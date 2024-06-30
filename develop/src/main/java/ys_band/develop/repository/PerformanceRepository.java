package ys_band.develop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByUser(User user);

    @Query(value = "SELECT * FROM Performance ORDER BY created_at DESC LIMIT 4", nativeQuery = true)
    List<Performance> findTop2ByOrderByCreatedAtDesc();
}