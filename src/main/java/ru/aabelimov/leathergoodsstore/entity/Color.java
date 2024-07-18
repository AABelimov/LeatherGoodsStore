package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
}
