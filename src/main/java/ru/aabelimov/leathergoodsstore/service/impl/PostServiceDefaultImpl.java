package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.entity.Post;
import ru.aabelimov.leathergoodsstore.mapper.PostMapper;
import ru.aabelimov.leathergoodsstore.repository.PostRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.PostService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceDefaultImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ImageService imageService;

    @Value("${path.to.images.for.posts}")
    private String imageDir;

    @Override
    @Transactional
    public void createPost(CreateOrUpdatePostDto dto, MultipartFile image) throws IOException {
        Post post = postMapper.toEntity(dto);
        post.setImage(imageService.createImage(image, imageDir));
        postRepository.save(post);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(); // TODO :: exception
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePost(Long id, CreateOrUpdatePostDto dto, MultipartFile image) throws IOException {
        Post post = getPost(id);

        if (!dto.title().isBlank()) {
            post.setTitle(dto.title());
        }
        if (!dto.text().isBlank()) {
            post.setText(dto.text());
        }
        if (!image.isEmpty()) {
            post.setImage(imageService.updateImage(post.getImage(), image, imageDir));
        }
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) throws IOException {
        Post post = getPost(id);
        postRepository.delete(post);
        imageService.deleteImage(post.getImage());
    }
}
