package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Leather;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;

import java.util.List;

public interface LeatherColorRepository extends JpaRepository<LeatherColor, Long> {

    List<LeatherColor> findAllByLeatherIdOrderById(Long leatherId);

    List<LeatherColor> findAllByLeatherIdAndIsVisibleOrderById(Long leatherId, Boolean isVisible);

    List<LeatherColor> findAllByColorIdOrderById(Long colorId);

    @Query("SELECT lc.leather FROM LeatherColor lc WHERE lc.isVisible = true ORDER BY lc.leather.id ASC")
    List<Leather> findAllLeathersWhereLeatherColorIsVisible(boolean isVisible);

    @Query("SELECT lc FROM LeatherColor lc ORDER BY lc.id ASC")
    List<LeatherColor> findAllOrderById();
}
