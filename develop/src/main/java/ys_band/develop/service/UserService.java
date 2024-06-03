package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    // 사용자 정보를 처리하는 UserRepository와 비밀번호를 암호화하는 BCryptPasswordEncoder를 주입받음
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 생성자를 통해 UserRepository와 BCryptPasswordEncoder 주입
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ID로 사용자를 조회하는 메서드
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // 사용자 이름으로 사용자를 조회하는 메서드
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 새로운 사용자를 저장하는 메서드
    public void save(User user) {
        // 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // UserDetailsService 인터페이스의 메서드를 구현하여 사용자 이름으로 사용자 정보를 로드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름으로 사용자 정보를 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Spring Security의 User 객체를 빌더 패턴으로 생성하여 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}