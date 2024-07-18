package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "leathers_colors")
public class LeatherColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Leather leather;

    @ManyToOne
    private Color color;  // TODO :: unique leather + color

//    @ManyToMany
//    @JoinTable(
//            name = "leathers_colors_images",
//            joinColumns = @JoinColumn(name = "leather_color_id"),
//            inverseJoinColumns = @JoinColumn(name = "image_id")
//    )
//    private List<Image> images;
    @ManyToOne
    private Image image;
}
