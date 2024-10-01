package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private Boolean isVisible;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Leather leather;

    @OneToOne
    private Image image;
}
