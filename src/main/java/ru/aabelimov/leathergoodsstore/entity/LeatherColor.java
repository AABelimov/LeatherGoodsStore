package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    private Color color;

    @ManyToOne
    private Image image;
    private Boolean isVisible;
}
