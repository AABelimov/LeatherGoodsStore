package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String description;

    @ManyToOne
    private Category category;

    @ManyToMany (fetch = FetchType.EAGER) // TODO :: possible differently? without eager
    @JoinTable(
            name = "products_images",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

//    @ManyToMany
//    @JoinTable(
//            name = "products_leather_colors",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )
//    private List<ProductLeatherColor> productLeatherColors;
}
