package ys_band.develop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public String sendVerificationEmail(String toEmail) {
        String verificationCode = generateVerificationCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Email Verification Code");
        message.setText("Your verification code is: " + verificationCode);
        message.setFrom(from);

        mailSender.send(message);

        return verificationCode;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // 테스트 메서드
    public void sendTestEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("k1999k@naver.com");
        message.setSubject("Test Email");
        message.setText("This is a test email from Spring Boot application.");
        message.setFrom(from);

        mailSender.send(message);
    }
}