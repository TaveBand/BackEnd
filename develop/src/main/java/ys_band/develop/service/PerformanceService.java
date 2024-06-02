package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;
import ys_band.develop.dto.performance.PerformanceDtoConverter;
import ys_band.develop.dto.performance.PerformancePostDto;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.repository.PerformanceRepository;
import ys_band.develop.repository.UserRepository;

import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository;

    // 생성자 주입을 통한 의존성 주입
    public PerformanceService(PerformanceRepository performanceRepository, UserRepository userRepository) {
        this.performanceRepository = performanceRepository;
        this.userRepository = userRepository;
    }

    /**
     * 모든 공연 정보를 조회합니다.
     *
     * @return 공연 정보 목록
     */
    public List<PerformanceGetDto> findAllPerformances() {
        List<Performance> performances = performanceRepository.findAll();
        return PerformanceDtoConverter.toPerformanceDtoList(performances);
    }

    /**
     * 공연 ID로 특정 공연 정보를 조회합니다.
     *
     * @param id 공연 ID
     * @return 공연 DTO (Optional)
     */
    public Optional<PerformanceGetDto> findPerformanceById(Long id) {
        Optional<Performance> performance = performanceRepository.findById(id);
        return performance.map(PerformanceDtoConverter::getPerformanceDto);
    }

    /**
     * 새로운 공연 정보를 저장합니다.
     *
     * @param performancePostDto 공연 정보 DTO
     * @return 저장된 공연 DTO
     */
    @Transactional
    public PerformanceGetDto savePerformance(PerformancePostDto performancePostDto) {
        User user = userRepository.findById(performancePostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + performancePostDto.getUserId()));
        Performance performance = PerformanceDtoConverter.postPerformanceEntity(performancePostDto, user);
        Performance savedPerformance = performanceRepository.save(performance);
        return PerformanceDtoConverter.getPerformanceDto(savedPerformance);
    }

    /**
     * 공연 정보를 삭제합니다.
     *
     * @param id 공연 ID
     */
    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    /**
     * 사용자 ID로 해당 사용자가 소유한 공연들을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자가 소유한 공연 목록
     */
    public List<PerformanceGetDto> findPerformancesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
        List<Performance> performances = performanceRepository.findByUser(user);
        return PerformanceDtoConverter.toPerformanceDtoList(performances);
    }
}