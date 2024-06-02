package ys_band.develop.dto.user;

import ys_band.develop.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoConverter {

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
        return users.stream().map(UserDtoConverter::toUserDto).collect(Collectors.toList());
    }
}