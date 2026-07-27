package com.canteen.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByFoodNameContainingIgnoreCase(String foodName);

    List<Food> findByCategoryIgnoreCase(String category);

    List<Food> findByAvailableDate(String availableDate);

    List<Food> findByQuantityLessThan(Integer quantity);

    List<Food> findByCanteenId(String canteenId);

    Long countByCanteenId(String canteenId);
}