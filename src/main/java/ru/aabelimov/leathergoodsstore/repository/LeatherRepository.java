package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.Leather;

import java.util.List;

public interface LeatherRepository extends JpaRepository<Leather, Long> {

    @Query(value = "SELECT * FROM leathers l1 WHERE id = (SELECT MIN(l2.id) FROM leathers l2 WHERE l2.name = l1.name)", nativeQuery = true)
    List<Leather> findLeathersWithoutDoubleName();

    List<Leather> findAllByIsVisible(Boolean isVisible);
}
