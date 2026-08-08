package com.canteen.management.service.impl;

import com.canteen.management.dto.RecommendationResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.entity.Student;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<RecommendationResponse> getRecommendations(String studentId) {

        Student student = studentRepository.findByStudentId(studentId).orElse(null);
        List<Food> foods = new ArrayList<>();
        if (student != null && student.getBranchId() != null) {
            foods = foodRepository.findByBranchId(student.getBranchId());
        } else {
            foods = foodRepository.findAll();
        }

        List<RecommendationResponse> recommendations = new ArrayList<>();

        for (Food food : foods) {

            if (food.getStatus() != null &&
                    food.getStatus().equalsIgnoreCase("Available")) {

                recommendations.add(

                        new RecommendationResponse(

                                food.getId(),
                                food.getFoodName(),
                                food.getImageUrl(),
                                food.getCategory() != null
                                        ? food.getCategory().getCategoryName()
                                        : null,
                                food.getPrice(),
                                "Recommended For You"

                        )

                );
            }
        }

        return recommendations;
    }
}