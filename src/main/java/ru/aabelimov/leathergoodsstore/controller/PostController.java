package ru.aabelimov.leathergoodsstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.service.CategoryService;
import ru.aabelimov.leathergoodsstore.service.PostService;

import java.io.IOException;

@Controller
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @PostMapping
    public String createPost(CreateOrUpdatePostDto dto,
                             @RequestParam MultipartFile image) throws IOException {
        postService.createPost(dto, image);
        return "redirect:/posts/settings";
    }

    @GetMapping("settings")
    public String getPostsSettingsPage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "post/posts-settings";
    }

    @GetMapping("{id}/edit")
    public String getPostEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "post/post-edit";
    }

    @PatchMapping("{id}")
    public String updatePost(@PathVariable Long id,
                              CreateOrUpdatePostDto dto,
                              @RequestParam MultipartFile image) throws IOException {
        postService.updatePost(id, dto, image);
        return "redirect:/posts/settings";
    }

    @DeleteMapping("{id}")
    public String deleteSlide(@PathVariable Long id) throws IOException {
        postService.deletePost(id);
        return "redirect:/posts/settings";
    }
}
