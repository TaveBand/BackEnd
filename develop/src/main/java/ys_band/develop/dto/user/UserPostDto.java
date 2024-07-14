package ys_band.develop.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDto {
    private String password;
    private String username;
    private String nickname;
    private String email;
    @JsonProperty("sessions")
    private List<Long> sessionIds;
}