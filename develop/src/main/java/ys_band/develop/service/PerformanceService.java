package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;
import ys_band.develop.dto.DtoConverter;
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

    public PerformanceService(PerformanceRepository performanceRepository, UserRepository userRepository) {
        this.performanceRepository = performanceRepository;
        this.userRepository = userRepository;
    }

    public List<PerformanceGetDto> findAllPerformances() {
        List<Performance> performances = performanceRepository.findAll();
        return DtoConverter.toPerformanceDtoList(performances);
    }

    public Optional<PerformanceGetDto> findPerformanceById(Long id) {
        Optional<Performance> performance = performanceRepository.findById(id);
        return performance.map(DtoConverter::getPerformanceDto);
    }

    @Transactional
    public PerformanceGetDto savePerformance(PerformancePostDto performancePostDto) {
        User user = userRepository.findById(performancePostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + performancePostDto.getUserId()));
        Performance performance = DtoConverter.postPerformanceEntity(performancePostDto, user);
        Performance savedPerformance = performanceRepository.save(performance);
        return DtoConverter.getPerformanceDto(savedPerformance);
    }

    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }
}