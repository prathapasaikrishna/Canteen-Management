package com.canteen.management.repository;

import com.canteen.management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryCode(String categoryCode);

    List<Category> findByBranch_Id(Long branchId);
}