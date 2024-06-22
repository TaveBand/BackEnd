package ys_band.develop.dto.jaehyun;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import ys_band.develop.domain.User;

/*
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickname;

    @Email
    private String email;

    //private Set<AuthorityDTO> authDTOSet;



    public static UserDTO fromEntity(User user) {
        if (user == null)
            return null;

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .authDTOSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))


                .build();
    }
}

    public static User toEntity(UserDTO userDTO){
        if (userDTO == null)
            return null;

        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .build();
    }
}
*/

