package com.canteen.management.service.impl;

import com.canteen.management.dto.RecommendationResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<RecommendationResponse> getRecommendations(String studentId) {

        List<Food> foods = foodRepository.findAll();

        List<RecommendationResponse> recommendations = new ArrayList<>();

        for (Food food : foods) {

            if (food.getStatus() != null &&
                    food.getStatus().equalsIgnoreCase("Available")) {

                recommendations.add(

                        new RecommendationResponse(

                                food.getId(),
                                food.getFoodName(),
                                food.getImageUrl(),
                                food.getCategory(),
                                food.getPrice(),
                                "Recommended For You"

                        )

                );
            }
        }

        return recommendations;
    }
}