package com.canteen.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Food;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByFoodNameContainingIgnoreCase(String foodName);

    List<Food> findByCategoryIgnoreCase(String category);

    List<Food> findByAvailableDate(String availableDate);

    List<Food> findByQuantityLessThan(Integer quantity);

    List<Food> findByCanteenId(String canteenId);

    Long countByCanteenId(String canteenId);

    Optional<Food> findById(Long id);

    List<Food> findByBranchId(Long branchId);

    List<Food> findByOrganizationId(Long organizationId);

    List<Food> findByOrganizationIdAndBranchId(
            Long organizationId,
            Long branchId
    );

    @Query("""
SELECT f
FROM Food f
WHERE f.organizationId = :organizationId
AND f.branchId = :branchId
""")
    List<Food> getFoods(
            @Param("organizationId") Long organizationId,
            @Param("branchId") Long branchId
    );


}