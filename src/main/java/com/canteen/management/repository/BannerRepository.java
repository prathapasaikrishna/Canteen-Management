package com.canteen.management.repository;

import com.canteen.management.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findByBranchId(Long branchId);

    @Query("SELECT b FROM Banner b WHERE b.branchId = :branchId OR b.branchId IS NULL")
    List<Banner> findByBranchIdOrGlobal(@Param("branchId") Long branchId);
}
