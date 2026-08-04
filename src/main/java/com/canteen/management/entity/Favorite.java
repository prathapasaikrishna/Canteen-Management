package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "favorites", indexes = {
    @Index(name = "idx_favorites_student", columnList = "studentId"),
    @Index(name = "idx_favorites_food", columnList = "foodId")
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentId;

    private Long foodId;
}