package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;

import java.util.List;

public interface LeatherColorRepository extends JpaRepository<LeatherColor, Long> {

    List<LeatherColor> findAllByLeatherId(Long leatherId);

    List<LeatherColor> findAllByColorId(Long colorId);

}
