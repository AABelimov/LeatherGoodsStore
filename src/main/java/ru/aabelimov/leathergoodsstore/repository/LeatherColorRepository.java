package ru.aabelimov.leathergoodsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.aabelimov.leathergoodsstore.entity.LeatherColor;

import java.util.List;

public interface LeatherColorRepository extends JpaRepository<LeatherColor, Long> {

    List<LeatherColor> findAllByLeatherId(Long leatherId);

    List<LeatherColor> findAllByColorId(Long colorId);

//    @Query("DELETE FROM LeatherColor lc WHERE lc.leather.id=?1")
//    @Modifying
//    void deleteLeathersColorsByLeatherId(Long leatherId);

//    @Query("SELECT lc FROM LeatherColor lc WHERE lc.leather.id = ?1 AND lc.color.name LIKE %?2%")
//    List<LeatherColor> findAllByLeatherIdAndColorName(Long leatherId, String colorName);
}
