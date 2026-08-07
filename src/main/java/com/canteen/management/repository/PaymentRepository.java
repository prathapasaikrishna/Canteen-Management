package com.canteen.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderNumber(String orderNumber);

    Optional<Payment> findByPaymentId(String paymentId);

    Long countByBranchId(Long branchId);

    List<Payment> findByBranchId(Long branchId);

    List<Payment> findByOrganizationId(Long organizationId);

    Double sumAmountByBranchId(Long branchId);

    @Query("""
SELECT COALESCE(SUM(p.amount),0)
FROM Payment p
WHERE p.branchId = :branchId
""")
    Double getTotalRevenue(@Param("branchId") Long branchId);

}