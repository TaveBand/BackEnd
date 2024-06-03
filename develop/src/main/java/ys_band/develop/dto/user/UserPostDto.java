package ys_band.develop.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostDto {
    private String schoolId;
    private String password;
    private String username;
    private String nickname;
    private String email;
    private String session;
}