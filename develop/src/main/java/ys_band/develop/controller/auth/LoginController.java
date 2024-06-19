package ys_band.develop.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/dailband")
    public String showLoginPage() {
        return "dailband";
    }
}