package com.canteen.management.repository;

import com.canteen.management.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {


    List<Food> findByFoodNameContainingIgnoreCase(String foodName);


    List<Food> findByCategory_CategoryNameIgnoreCase(String categoryName);


    List<Food> findByAvailableDate(String date);


    List<Food> findByQuantityLessThan(Integer quantity);


    List<Food> findByOrganizationIdAndBranchId(
            Long organizationId,
            Long branchId
    );

    List<Food> findByOrganizationIdAndBranchIdAndStatus(
            Long organizationId,
            Long branchId,
            String status
    );

    Long countByCanteenId(String canteenId);

    Long countByBranchId(Long branchId);

    Long countByBranchIdAndStatus(Long branchId, String available);
}