package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ManyToOne
    private Image image;
}
