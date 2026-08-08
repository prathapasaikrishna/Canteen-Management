    package com.canteen.management.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    @Entity
    @Table(name = "student")
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "student_id")
        private String studentId;

        @NotBlank(message = "Name is required")
        @Column(name = "name", nullable = false)
        private String name;

        @Email(message = "Enter a valid email")
        @NotBlank(message = "Email is required")
        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must contain at least 6 characters")
        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "department")
        private String department;

        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain exactly 10 digits")
        @Column(name = "mobile_number", nullable = false)
        private String mobileNumber;

        @Column(name = "year")
        private String year;

        @NotBlank(message = "Role is required")
        @Column(name = "role", nullable = false)
        private String role;



        @Column(name = "canteen_id")
        private String canteenId;

        @Column(name = "fcm_token", length = 500)
        private String fcmToken;

        private Long organizationId;

        private Long branchId;


        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;


        @Column(name = "user_type")
        private String userType;

        @Column(name = "account_status")
        private String accountStatus;

        @Column(name = "loyalty_points")
        private Integer loyaltyPoints = 0;

        @Column(name = "loyalty_tier")
        private String loyaltyTier = "SILVER";

        @Column(name = "profile_url", length = 500)
        private String profileUrl;
    }