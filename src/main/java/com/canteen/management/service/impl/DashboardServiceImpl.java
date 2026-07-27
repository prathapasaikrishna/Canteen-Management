package com.canteen.management.service.impl;

import com.canteen.management.dto.DashboardResponse;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.repository.ReviewRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.service.DashboardService;
import com.canteen.management.entity.Food;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public DashboardResponse getDashboard() {


        DashboardResponse response = new DashboardResponse();

        response.setTotalStudents(studentRepository.count());

        response.setTotalFoods(foodRepository.count());

        response.setTotalOrders(orderRepository.count());

        Double revenue = orderRepository.getTotalRevenue();

        if (revenue == null) {
            revenue = 0.0;
        }

        response.setTotalRevenue(revenue);

        List<Long> foodIds = orderRepository.getMostOrderedFoodIds();

        if (!foodIds.isEmpty()) {

            Long foodId = foodIds.get(0);

            Optional<Food> food = foodRepository.findById(foodId);

            if (food.isPresent()) {

                response.setMostOrderedFood(
                        food.get().getFoodName()
                );

                response.setMostOrderedCount(
                        orderRepository.countByFoodId(foodId)
                );

            }

        }

        List<Long> topRatedFoodIds = reviewRepository.getTopRatedFoodIds();

        if (!topRatedFoodIds.isEmpty()) {

            Long topFoodId = topRatedFoodIds.get(0);

            Optional<Food> food = foodRepository.findById(topFoodId);

            if (food.isPresent()) {

                response.setTopRatedFood(
                        food.get().getFoodName()
                );

                Double rating = reviewRepository.getAverageRating(topFoodId);

                if (rating == null) {
                    rating = 0.0;
                }

                response.setTopRating(
                        Math.round(rating * 10.0) / 10.0
                );
            }
        }

        return response;
    }
}