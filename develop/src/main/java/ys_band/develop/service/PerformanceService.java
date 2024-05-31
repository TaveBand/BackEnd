package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;
import ys_band.develop.dto.DtoConverter;
import ys_band.develop.dto.performance.PerformanceCreateDto;
import ys_band.develop.dto.performance.PerformanceDto;
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

    public List<PerformanceDto> findAllPerformances() {
        List<Performance> performances = performanceRepository.findAll();
        return DtoConverter.toPerformanceDtoList(performances);
    }

    public Optional<PerformanceDto> findPerformanceById(Long id) {
        Optional<Performance> performance = performanceRepository.findById(id);
        return performance.map(DtoConverter::toPerformanceDto);
    }

    @Transactional
    public PerformanceDto savePerformance(PerformanceCreateDto performanceCreateDto) {
        User user = userRepository.findById(performanceCreateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + performanceCreateDto.getUserId()));
        Performance performance = DtoConverter.toPerformanceEntity(performanceCreateDto, user);
        Performance savedPerformance = performanceRepository.save(performance);
        return DtoConverter.toPerformanceDto(savedPerformance);
    }

    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }
}