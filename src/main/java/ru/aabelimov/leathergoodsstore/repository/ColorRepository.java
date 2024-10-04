package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Color;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("SELECT c FROM Color c WHERE c.name LIKE %?1% ORDER BY c.id ASC")
    List<Color> getColorsByName(String colorName);
}
