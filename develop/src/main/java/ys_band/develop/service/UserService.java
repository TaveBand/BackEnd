package ys_band.develop.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.User;
import ys_band.develop.dto.user.UserPostDto;
import ys_band.develop.dto.user.UserGetDto;
import ys_band.develop.dto.user.UserDtoConverter;
import ys_band.develop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // UserDto를 세션에 저장
        UserGetDto userDto = UserDtoConverter.toUserGetDto(user);
        session.setAttribute("user", userDto);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUsernameUnique(String username) {
        return !userRepository.findByUsername(username).isPresent();
    }

    public void save(UserPostDto userPostDto) {
        if (!isUsernameUnique(userPostDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        User user = UserDtoConverter.toUserEntity(userPostDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}