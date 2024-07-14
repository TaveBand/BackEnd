package ys_band.develop.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ys_band.develop.dto.JwtTokenDTO;
import ys_band.develop.dto.LoginDTO;
import ys_band.develop.security.JwtAuthenticationFilter;
import ys_band.develop.security.JwtTokenProvider;

@RestController
@RequestMapping("/dailband")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public LoginController(JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder =authenticationManagerBuilder;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //List<GrantedAuthority> roles = new ArrayList<>();
        //roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        logger.debug("Authentication principal: {}", authentication.getPrincipal());


        String jwt = jwtTokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTH_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JwtTokenDTO(jwt), httpHeaders, HttpStatus.OK);

    }
    //로그아웃
    /*@PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> logout(){
        Respo
    }*/

}