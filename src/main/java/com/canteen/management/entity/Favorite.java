package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentId;

    private Long foodId;
}