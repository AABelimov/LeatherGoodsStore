package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aabelimov.leathergoodsstore.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
