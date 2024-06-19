package ys_band.develop.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ys_band.develop.domain.User;
import ys_band.develop.dto.user.UserGetDto;
import ys_band.develop.dto.user.UserDtoConverter;
import ys_band.develop.dto.user.UserPostDto;
import ys_band.develop.service.EmailService;
import ys_band.develop.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private String savedVerificationCode;
    private String savedEmail;

    @GetMapping("/dailband/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userPostDto", new UserPostDto());
        return "register";
    }

    @PostMapping("/dailband/register")
    public String registerUser(@ModelAttribute UserPostDto userPostDto, Model model) {
        if (!userService.isUsernameUnique(userPostDto.getUsername())) {
            model.addAttribute("errorMessage", "Username is already taken");
            return "register";
        }
        // 인증 코드는 클라이언트에서 검증
        userService.save(userPostDto);
        return "redirect:/login";
    }

    @GetMapping("/dailband/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean isUnique = userService.isUsernameUnique(username);
        return ResponseEntity.ok(isUnique);
    }

    @PostMapping("/dailband/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        savedEmail = email;
        savedVerificationCode = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dailband/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = email.equals(savedEmail) && code.equals(savedVerificationCode);
        return ResponseEntity.ok(isValid);
    }
}