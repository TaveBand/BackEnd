package ys_band.develop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.User;
import ys_band.develop.dto.DtoConverter;
import ys_band.develop.dto.user.UserCreateDto;
import ys_band.develop.dto.user.UserDto;
import ys_band.develop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return DtoConverter.toUserDtoList(users);
    }

    public Optional<UserDto> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(DtoConverter::toUserDto);
    }

    public Optional<UserDto> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(DtoConverter::toUserDto);
    }

    @Transactional
    public UserDto save(UserCreateDto userCreateDto) {
        User user = DtoConverter.toUserEntity(userCreateDto);
        User savedUser = userRepository.save(user);
        return DtoConverter.toUserDto(savedUser);
    }

    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of(DtoConverter.toUserDto(user.get()));
        }
        return Optional.empty();
    }
}