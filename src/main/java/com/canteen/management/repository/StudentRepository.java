package com.canteen.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

}