package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_variants")
@Data
@NoArgsConstructor
public class FoodVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(nullable = false)
    private String name; // e.g. "Half", "Full", "Family Pack"

    @Column(nullable = false)
    private Double price; // pricing for this variant
}
