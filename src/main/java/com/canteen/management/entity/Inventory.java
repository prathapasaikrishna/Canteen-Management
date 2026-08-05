package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "idx_inventory_branch", columnList = "branchId")
})
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long branchId;

    @Column(nullable = false)
    private String itemName; // e.g. "Rice", "Oil", "Chicken"

    @Column(nullable = false)
    private Double quantity; // in KG/Liters

    @Column(nullable = false)
    private String unit; // e.g. "KG", "Liters", "Packets"

    @Column(nullable = false)
    private Double alertThreshold; // Quantity below which to alert

    @Column(nullable = false)
    private String status = "OK"; // OK / LOW_STOCK

    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (quantity != null && alertThreshold != null) {
            status = quantity <= alertThreshold ? "LOW_STOCK" : "OK";
        }
    }
}
