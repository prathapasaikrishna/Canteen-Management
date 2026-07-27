package com.canteen.management.repository;

import com.canteen.management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByFoodIdOrderByIdDesc(Long foodId);

    @org.springframework.data.jpa.repository.Query(
            "SELECT AVG(r.rating) FROM Review r WHERE r.foodId = :foodId"
    )
    Double getAverageRating(Long foodId);


    Long countByFoodId(Long foodId);

}