package com.canteen.management.repository;

import com.canteen.management.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByStudentId(String studentId);

    Optional<Favorite> findByStudentIdAndFoodId(String studentId, Long foodId);

    void deleteByStudentIdAndFoodId(String studentId, Long foodId);
}