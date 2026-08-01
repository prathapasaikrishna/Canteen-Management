package com.canteen.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.canteen.management.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Student> findByStudentId(String studentId);


}