package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Slide;

import java.util.List;

public interface SlideRepository extends JpaRepository<Slide, Long> {

    @Query("SELECT s FROM Slide s ORDER BY s.id ASC")
    List<Slide> findAllOrderById();
}
