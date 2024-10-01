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
    private Boolean isVisible;

    // TODO :: try to remove the eager
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "leathers_images",
            joinColumns = @JoinColumn(name = "leather_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @OrderBy("id DESC")
    private List<Image> images;
}
