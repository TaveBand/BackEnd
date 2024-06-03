package ys_band.develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ys_band.develop.domain.User;
import ys_band.develop.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    // 사용자 정보를 처리하는 UserService를 주입받음
    private final UserService userService;

    // "/home" 경로로 GET 요청이 들어오면 이 메서드가 처리함
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // 인증된 사용자의 정보가 userDetails에 저장되어 있음
        if (userDetails != null) {
            // userDetails에서 사용자 이름을 가져와서 데이터베이스에서 사용자 정보를 조회함
            User user = userService.findByUsername(userDetails.getUsername()).orElse(null);
            // 사용자가 존재하면 모델에 사용자 이름과 이메일을 추가함
            if (user != null) {
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
            }
        }
        // "home" 템플릿을 반환하여 사용자에게 표시함
        return "home";
    }
}