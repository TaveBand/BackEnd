package ys_band.develop.dto;


import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;
import ys_band.develop.dto.performance.PerformancePostDto;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.dto.user.UserCreateDto;
import ys_band.develop.dto.user.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static PerformanceGetDto getPerformanceDto(Performance performance) {
        PerformanceGetDto dto = new PerformanceGetDto();
        dto.setPerformanceId(performance.getPerformance_id());
        dto.setTitle(performance.getTitle());
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
        return performances.stream().map(DtoConverter::getPerformanceDto).collect(Collectors.toList());
    }

    // UserDto 관련 변환기 추가
    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUser_id());
        dto.setSchoolId(user.getSchoolId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User toUserEntity(UserCreateDto dto) {
        User user = new User();
        user.setSchoolId(dto.getSchoolId());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(DtoConverter::toUserDto).collect(Collectors.toList());
    }
}