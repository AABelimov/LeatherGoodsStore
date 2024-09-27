package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "products_leather_colors")
@EqualsAndHashCode(of = "id")
public class ProductLeatherColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private LeatherColor leatherColor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "plc_images",
            joinColumns = @JoinColumn(name = "plc_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;
}
