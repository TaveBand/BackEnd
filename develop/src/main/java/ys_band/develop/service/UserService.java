package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.User;
import ys_band.develop.dto.performance.PerformanceDtoConverter;
import ys_band.develop.dto.user.UserCreateDto;
import ys_band.develop.dto.user.UserDto;
import ys_band.develop.dto.user.UserDtoConverter;
import ys_band.develop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 생성자 주입을 통한 의존성 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 모든 사용자 정보를 조회합니다.
     *
     * @return 사용자 정보 목록
     */
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return UserDtoConverter.toUserDtoList(users);
    }

    /**
     * 사용자 ID로 특정 사용자 정보를 조회합니다.
     *
     * @param id 사용자 ID
     * @return 사용자 DTO (Optional)
     */
    public Optional<UserDto> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserDtoConverter::toUserDto);
    }

    /**
     * 사용자 이름으로 특정 사용자 정보를 조회합니다.
     *
     * @param username 사용자 이름
     * @return 사용자 DTO (Optional)
     */
    public Optional<UserDto> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserDtoConverter::toUserDto);
    }

    /**
     * 새로운 사용자 정보를 저장합니다.
     *
     * @param userCreateDto 사용자 정보 DTO
     * @return 저장된 사용자 DTO
     */
    @Transactional
    public UserDto save(UserCreateDto userCreateDto) {
        User user = UserDtoConverter.toUserEntity(userCreateDto);
        User savedUser = userRepository.save(user);
        return UserDtoConverter.toUserDto(savedUser);
    }

    /**
     * 사용자 이름과 비밀번호로 로그인합니다.
     *
     * @param username 사용자 이름
     * @param password 비밀번호
     * @return 사용자 DTO (Optional)
     */
    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of(UserDtoConverter.toUserDto(user.get()));
        }
        return Optional.empty();
    }
}