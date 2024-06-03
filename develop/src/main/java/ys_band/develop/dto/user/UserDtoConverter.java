package ys_band.develop.dto.user;

import ys_band.develop.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class UserDtoConverter {

    public static User toUserEntity(UserPostDto dto) {
        User user = new User();
        user.setSchoolId(dto.getSchoolId());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setSession(dto.getSession());
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        return user;
    }

    public static UserGetDto toUserGetDto(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setUserId(user.getUser_id());
        dto.setSchoolId(user.getSchoolId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        dto.setSession(user.getSession());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setModifiedAt(user.getModifiedAt());
        return dto;
    }

    public static List<UserGetDto> toUserDtoList(List<User> users) {
        return users.stream().map(UserDtoConverter::toUserGetDto).collect(Collectors.toList());
    }

}