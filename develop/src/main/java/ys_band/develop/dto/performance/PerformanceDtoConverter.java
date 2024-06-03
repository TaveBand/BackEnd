package ys_band.develop.dto.performance;


import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class PerformanceDtoConverter {

    public static PerformanceGetDto getPerformanceDto(Performance performance) {
        PerformanceGetDto dto = new PerformanceGetDto();
        dto.setPerformanceId(performance.getPerformance_id());
        dto.setTitle(performance.getTitle());
        dto.setContent(performance.getContent());
        dto.setDate(performance.getDate());
        dto.setTime(performance.getTime());
        dto.setVenue(performance.getVenue());
        dto.setTotalSeats(performance.getTotal_seats());
        dto.setCurrentSeats(performance.getCurrent_seats());
        dto.setImagePath(performance.getImage_path());
        return dto;
    }

    public static Performance postPerformanceEntity(PerformancePostDto dto, User user) {
        Performance performance = new Performance();
        performance.setTitle(dto.getTitle());
        performance.setContent(dto.getContent());
        performance.setDate(dto.getDate());
        performance.setTime(dto.getTime());
        performance.setVenue(dto.getVenue());
        performance.setTotal_seats(dto.getTotalSeats());
        performance.setCurrent_seats(0); // 초기 현재 좌석 수는 총 좌석 수와 동일
        performance.setImage_path(dto.getImagePath());
        performance.setUser(user);
        return performance;
    }

    public static List<PerformanceGetDto> toPerformanceDtoList(List<Performance> performances) {
        return performances.stream().map(PerformanceDtoConverter::getPerformanceDto).collect(Collectors.toList());
    }


}