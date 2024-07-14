package ys_band.develop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ys_band.develop.domain.User;

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
                /*.authDTOSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                */

                .build();
    }
}

