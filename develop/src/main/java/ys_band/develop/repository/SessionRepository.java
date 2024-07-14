package ys_band.develop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ys_band.develop.domain.Session;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllById(Iterable<Long> sessionIds);  // 세션 ID로 세션 객체들을 조회
}
