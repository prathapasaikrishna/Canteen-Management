package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;


    @Column(name = "mobile_number")
    private String mobileNumber;


    /*
        Roles:

        CUSTOMER
        ADMIN
        SUPER_ADMIN
    */
    @Column(nullable = false)
    private String role;



    /*
        Organization Mapping

        College
        Hotel
        Restaurant
    */
    @Column(name = "organization_id")
    private Long organizationId;



    /*
        Branch Mapping

        Main Canteen
        Hotel Branch
        Restaurant Branch
    */
    @Column(name = "branch_id")
    private Long branchId;



    /*
        CUSTOMER / ADMIN / SUPER_ADMIN
    */
    @Column(name = "user_type")
    private String userType;



    /*
        ACTIVE / BLOCKED
    */
    @Column(name = "account_status")
    private String accountStatus = "ACTIVE";



    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}