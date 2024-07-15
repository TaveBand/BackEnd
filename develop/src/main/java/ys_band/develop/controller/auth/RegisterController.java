package ys_band.develop.controller.auth;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.UserDTO;
import ys_band.develop.service.EmailService;
import ys_band.develop.service.RegisterService;

import java.util.Map;

@RestController
@RequestMapping("/dailband")
public class RegisterController {
    private final RegisterService registerService;
    private final EmailService emailService;

    private String savedVerificationCode;
    private String savedEmail;

    public RegisterController(RegisterService registerService, EmailService emailService) {
        this.registerService = registerService;
        this.emailService = emailService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(registerService.register(userDTO));
    }

    // 이메일 인증 코드 전송
    @PostMapping("/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            savedEmail = email;
            savedVerificationCode = emailService.sendVerificationEmail(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 인증 코드 확인
    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        boolean isValid = email.equals(savedEmail) && code.equals(savedVerificationCode);
        return ResponseEntity.ok(isValid);
    }
}
