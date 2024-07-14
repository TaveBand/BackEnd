package ys_band.develop.controller.auth;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.UserDTO;
import ys_band.develop.service.EmailService;
import ys_band.develop.service.RegisterService;

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


    //회원가입
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok((registerService.register(userDTO)));
    }


    @PostMapping("/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        savedEmail = email;
        savedVerificationCode = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = email.equals(savedEmail) && code.equals(savedVerificationCode);
        return ResponseEntity.ok(isValid);
    }

}
