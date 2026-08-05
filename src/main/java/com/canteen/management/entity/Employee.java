package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees", indexes = {
    @Index(name = "idx_employees_branch", columnList = "branchId")
})
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long branchId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role; // Manager, Cashier, Chef, Cook, Helper, Cleaner, Delivery Boy

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, LEAVE, INACTIVE

    private LocalDateTime hiredAt;

    @PrePersist
    public void onCreate() {
        hiredAt = LocalDateTime.now();
    }
}
