package ys_band.develop.dto.user;

import ys_band.develop.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class UserDtoConverter {


    public static UserGetDto toUserGetDto(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setUserId(user.getUserId());
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