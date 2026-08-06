package com.canteen.management.repository;

import com.canteen.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);


    Long countByOrganizationId(Long organizationId);


    Long countByBranchId(Long branchId);

}