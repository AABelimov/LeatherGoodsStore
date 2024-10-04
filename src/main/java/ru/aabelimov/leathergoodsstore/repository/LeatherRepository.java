package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Leather;

import java.util.List;

public interface LeatherRepository extends JpaRepository<Leather, Long> {

    List<Leather> findAllByIsVisibleOrderById(Boolean isVisible);

    @Query("SELECT l FROM Leather l ORDER BY l.id ASC")
    List<Leather> findAllOrderById();
}
