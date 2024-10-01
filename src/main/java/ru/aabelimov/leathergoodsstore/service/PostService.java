package ru.aabelimov.leathergoodsstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.entity.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post createPost(CreateOrUpdatePostDto dto, String reference, Long referenceId);

    Post getPost(Long id);

    List<Post> getAllPosts();

    List<Post> getAllVisiblePosts();

    void updatePost(Long id, CreateOrUpdatePostDto dto, MultipartFile image) throws IOException;

    void changeVisibility(Long id);

    void deletePost(Long id) throws IOException;
}
