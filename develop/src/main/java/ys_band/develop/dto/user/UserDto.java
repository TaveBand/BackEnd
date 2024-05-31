package ys_band.develop.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {      //  to 클라이언트
    private Long userId;
    private String schoolId;
    private String username;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
}