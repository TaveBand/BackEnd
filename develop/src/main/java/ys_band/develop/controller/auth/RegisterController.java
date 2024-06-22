package ys_band.develop.controller.auth;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ys_band.develop.controller.ReservationController;
import ys_band.develop.domain.User;
import ys_band.develop.dto.user.UserGetDto;
import ys_band.develop.dto.user.UserDtoConverter;
import ys_band.develop.dto.user.UserPostDto;
import ys_band.develop.service.EmailService;
import ys_band.develop.service.UserService;


/**
 * RegisterController는 사용자 등록 및 이메일 인증을 처리합니다.
 */
@Controller
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;

    private String savedVerificationCode;
    private String savedEmail;

    /**
     * RegisterController의 생성자입니다.
     *
     * @param userService 사용자 관련 작업을 처리하는 서비스
     * @param emailService 이메일 관련 작업을 처리하는 서비스
     */
    @Autowired
    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    /**
     * 사용자 등록 폼을 보여줍니다.
     *
     * @param model 뷰에 전달할 모델
     * @return 사용자 등록 폼 뷰의 이름
     */
    @GetMapping("/dailband/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userPostDto", new UserPostDto());
        return "register";
    }

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param userPostDto 등록할 사용자 정보를 담은 DTO
     * @param model 뷰에 전달할 모델
     * @return 등록 성공 시 로그인 페이지로 리다이렉트
     */
    @PostMapping("/dailband/register")
    public String registerUser(@ModelAttribute UserPostDto userPostDto, Model model) {
        if (!userService.isUsernameUnique(userPostDto.getUsername())) {
            model.addAttribute("errorMessage", "Username is already taken");
            return "register";
        }
        userService.save(userPostDto);
        return "redirect:/login";
    }

    /**
     * 사용자 이름의 고유성을 확인합니다.
     *
     * @param username 확인할 사용자 이름
     * @return 사용자 이름이 고유한지 여부를 담은 ResponseEntity
     */
    @GetMapping("/dailband/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean isUnique = userService.isUsernameUnique(username);
        return ResponseEntity.ok(isUnique);
    }

    /**
     * 이메일로 인증 코드를 전송합니다.
     *
     * @param email 인증 코드를 보낼 이메일 주소
     * @return 인증 코드 전송 성공 여부를 담은 ResponseEntity
     */
    @PostMapping("/dailband/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        savedEmail = email;
        savedVerificationCode = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 입력한 인증 코드를 검증합니다.
     *
     * @param email 사용자가 입력한 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     * @return 인증 코드가 유효한지 여부를 담은 ResponseEntity
     */
    @PostMapping("/dailband/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = email.equals(savedEmail) && code.equals(savedVerificationCode);
        return ResponseEntity.ok(isValid);
    }
}