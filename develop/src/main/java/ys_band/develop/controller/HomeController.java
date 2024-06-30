package ys_band.develop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ys_band.develop.domain.Performance;
import ys_band.develop.domain.User;
import ys_band.develop.dto.mypr.MyPrGetDTO;
import ys_band.develop.dto.mypr.MyPrPostDTOWithoutComments;
import ys_band.develop.dto.performance.PerformanceGetDto;
import ys_band.develop.service.MyPrService;
import ys_band.develop.service.PerformanceService;
import ys_band.develop.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailband")
public class HomeController {

    private final PerformanceService performanceService;
    private final MyPrService myPrService;

    public HomeController(PerformanceService performanceService, MyPrService myPrService) {
        this.performanceService = performanceService;
        this.myPrService = myPrService;
    }

    @GetMapping("/home")
    public Map<String, Object> home() {
        List<PerformanceGetDto> performances = performanceService.findAllPerformances();
        List<MyPrPostDTOWithoutComments> posts = myPrService.getAllMyPrPosts();

        return Map.of("performances", performances, "posts", posts); // 여기다가 추가 방식?
    }
}