package ys_band.develop.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserGetDto {
    private Long userId;
    private String schoolId;
    private String username;
    private String nickname;
    private String email;
    private String session;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}