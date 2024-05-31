package ys_band.develop.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {    // to 서버
    private String schoolId;
    private String password;
    private String username;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
}