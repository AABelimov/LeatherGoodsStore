package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.entity.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {

    void createPost(CreateOrUpdatePostDto dto, MultipartFile image) throws IOException;

    Post getPost(Long id);

    List<Post> getAllPosts();

    void updatePost(Long id, CreateOrUpdatePostDto dto, MultipartFile image) throws IOException;

    void deletePost(Long id) throws IOException;
}
