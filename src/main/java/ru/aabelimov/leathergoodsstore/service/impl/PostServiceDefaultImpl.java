package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.Post;
import ru.aabelimov.leathergoodsstore.entity.Product;
import ru.aabelimov.leathergoodsstore.repository.PostRepository;
import ru.aabelimov.leathergoodsstore.service.ImageService;
import ru.aabelimov.leathergoodsstore.service.LeatherService;
import ru.aabelimov.leathergoodsstore.service.PostService;
import ru.aabelimov.leathergoodsstore.service.ProductService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceDefaultImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final ProductService productService;
    private final LeatherService leatherService;

    @Value("${path.to.images.for.posts}")
    private String imageDir;

    @Override
    @Transactional
    public Post createPost(CreateOrUpdatePostDto dto, String reference, Long referenceId) {
        Post post = new Post();
        post.setIsVisible(false);
        if (reference.equals("product")) {
            Product product = productService.getProduct(referenceId);
            post.setProduct(product);
            post.setTitle(product.getName());
            post.setText(product.getDescription());
            post.setImage(product.getImages().get(0));
        } else if (reference.equals("leather")) {
            Leather leather = leatherService.getLeather(referenceId);
            post.setLeather(leather);
            post.setTitle(leather.getName());
            post.setText(leather.getDescription());
            post.setImage(leather.getImages().get(0));
        }
        return postRepository.save(post);
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
    public List<Post> getAllVisiblePosts() {
        return postRepository.findAllByIsVisible(true);
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
            if (post.getImage() == null) {
                post.setImage(imageService.createImage(image, imageDir));
            } else {
                imageService.updateImage(post.getImage(), image, imageDir);
            }
        }
        postRepository.save(post);
    }

    @Override
    public void changeVisibility(Long id) {
        Post post = getPost(id);
        post.setIsVisible(!post.getIsVisible());
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
