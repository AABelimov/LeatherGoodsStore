package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIsVisibleOrderById(Boolean isVisible);

    @Query("SELECT p FROM Post p ORDER BY p.id ASC")
    List<Post> findAllOrderById();
}
