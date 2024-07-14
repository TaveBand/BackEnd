package ys_band.develop.service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys_band.develop.domain.Post;
import ys_band.develop.domain.Session;
import ys_band.develop.domain.User;
import ys_band.develop.dto.post.*;
import ys_band.develop.dto.user.*;
import ys_band.develop.repository.PostRepository;
import ys_band.develop.repository.SessionRepository;
import ys_band.develop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ys_band.develop.dto.user.UserDtoConverter.toUserGetDto;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;

    }


    //현재 사용자 정보 가져오기

    public UserGetDto getUserProfile(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return toUserGetDto(user);

        //return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername);
    }
    //사용자 개인정보 수정

    //LazyInitializationException 예외는 JPA의 영속성 컨텍스트가 종료된 후에 연관관계가 설정된 엔티티를 조회하려고 할 때 발생하기 때문에 세션이 유지되도록 트랜잭션을 설정해줍니다. 서비스 계층에서 트랜잭션을 시작하면 Repository까지 해당 트랜잭션이 전파되어 사용됩니다. 따라서 지연 로딩 시점까지 세션을 유지하여 사용할 수 있습니다.
    @Transactional
    public UserGetDto updateUserProfile(String username, UserPostDto userDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        System.out.println("세션 정보 입력 전");
        List<Long> sessionIds = userDTO.getSessionIds();
        if (sessionIds == null) {
            sessionIds = new ArrayList<>();
        }
        System.out.println("세션 ID 리스트: " + sessionIds);
        updateUserSessions(user, sessionIds);
        System.out.println("유저 세션 리스트: " + user.getSessions());

        System.out.println("세션 정보 업데이트 후");
        userRepository.save(user);
        System.out.println(user);
        return toUserGetDto(user);
    }

    //사용자가 작성한 게시물 조회
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserUserId(userId);
        return posts.stream().map(PostDTO::fromEntity).collect(Collectors.toList());
    }

    //관리자 권한으로 사용자 정보 가져오기
    @Transactional
    public Optional<User> getUserByAdmin(String username) {
        return userRepository.findByUsername(username);

    }


    public Long getUserIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getUserId();

    }

    private void updateUserSessions(User user, List<Long> sessionIds) {
        System.out.println("업데이트할 세션 ID 리스트: " + sessionIds);
        user.getSessions().clear();

        // 새로운 세션 정보 추가
        if (sessionIds != null && !sessionIds.isEmpty()) {
            List<Session> sessions = sessionRepository.findAllById(sessionIds);
            user.setSessions(sessions);
        }


    }
}
