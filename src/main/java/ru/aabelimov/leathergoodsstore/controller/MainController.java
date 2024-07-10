package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.aabelimov.leathergoodsstore.service.PostService;
import ru.aabelimov.leathergoodsstore.service.SlideService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SlideService slideService;
    private final PostService postService;

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("slides", slideService.getAllSlides());
        model.addAttribute("posts", postService.getAllPosts());
        return "main/main";
    }
}
