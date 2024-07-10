package ru.aabelimov.leathergoodsstore.mapper;


import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrUpdatePostDto;
import ru.aabelimov.leathergoodsstore.entity.Post;

@Component
public class PostMapper {

    public Post toEntity(CreateOrUpdatePostDto dto) {
        Post post = new Post();
        post.setTitle(dto.title());
        post.setText(dto.text());
        return post;
    }
}
