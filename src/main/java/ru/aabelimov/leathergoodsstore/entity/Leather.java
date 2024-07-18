package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "leathers")
public class Leather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

//    @ManyToOne
//    private Color color;

    @ManyToMany
    @JoinTable(
            name = "leathers_images",
            joinColumns = @JoinColumn(name = "leather_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    @ManyToMany
    @JoinTable(
            name = "leathers_colors",
            joinColumns = @JoinColumn(name = "leather_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<LeatherColor> leatherColors;
}
