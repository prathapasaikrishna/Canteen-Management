package com.canteen.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.canteen.management.service.CloudinaryService;
import com.canteen.management.service.NotificationService;
import com.canteen.management.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.service.FoodService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReviewRepository reviewRepository;

    private FoodResponse mapToResponse(Food food, String message) {
        Double avgRating = reviewRepository.getAverageRating(food.getId());
        Long totalRev = reviewRepository.countByFoodId(food.getId());
        
        double ratingVal = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;
        long revCount = totalRev != null ? totalRev : 0L;

        return new FoodResponse(
                food.getId(),
                food.getFoodName(),
                food.getCategory(),
                food.getPrice(),
                food.getImageUrl(),
                food.getAvailableDate(),
                food.getAvailableTime(),
                food.getQuantity(),
                food.getStatus(),
                food.getCanteenId(),

                message,
                ratingVal,
                revCount
        );
    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile image) {
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(image);
        }

        Food food = new Food();
        food.setFoodName(foodRequest.getFoodName());
        food.setCategory(foodRequest.getCategory());
        food.setPrice(foodRequest.getPrice());
        food.setImageUrl(imageUrl);
        food.setAvailableDate(foodRequest.getAvailableDate());
        food.setAvailableTime(foodRequest.getAvailableTime());
        food.setQuantity(foodRequest.getQuantity());
        food.setStatus(foodRequest.getStatus());
        food.setCanteenId(foodRequest.getCanteenId());

        food.setOrganizationId(
                foodRequest.getOrganizationId()
        );

        food.setBranchId(
                foodRequest.getBranchId()
        );

        Food savedFood = foodRepository.save(food);

        notificationService.notifyAllStudents(
                "🍔 New Food Available",
                savedFood.getFoodName() + " is Available Today."
        );

        return mapToResponse(savedFood, "Food Added Successfully");
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        List<FoodResponse> responseList = new ArrayList<>();
        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }
        return responseList;
    }

    @Override
    public FoodResponse getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food Not Found"));
        return mapToResponse(food, "Food Found Successfully");
    }

    @Override
    public FoodResponse updateFood(Long id, Food foodRequest, MultipartFile image) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food Not Found"));

        food.setFoodName(foodRequest.getFoodName());
        food.setCategory(foodRequest.getCategory());
        food.setPrice(foodRequest.getPrice());
        food.setAvailableDate(foodRequest.getAvailableDate());
        food.setAvailableTime(foodRequest.getAvailableTime());
        food.setQuantity(foodRequest.getQuantity());
        food.setStatus(foodRequest.getStatus());
        food.setCanteenId(foodRequest.getCanteenId());

        if (image != null && !image.isEmpty()) {
            food.setImageUrl(cloudinaryService.uploadImage(image));
        }

        Food updatedFood = foodRepository.save(food);
        return mapToResponse(updatedFood, "Food Updated Successfully");
    }

    @Override
    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food Not Found"));
        foodRepository.delete(food);
    }

    @Override
    public List<FoodResponse> searchFood(String foodName) {
        List<Food> foods = foodRepository.findByFoodNameContainingIgnoreCase(foodName);
        List<FoodResponse> responseList = new ArrayList<>();
        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }
        return responseList;
    }

    @Override
    public List<FoodResponse> getFoodByCategory(String category) {
        List<Food> foods = foodRepository.findByCategoryIgnoreCase(category);
        List<FoodResponse> responseList = new ArrayList<>();
        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }
        return responseList;
    }

    @Override
    public List<FoodResponse> getTodayMenu() {
        String today = LocalDate.now().toString();
        List<Food> foods = foodRepository.findByAvailableDate(today);
        List<FoodResponse> responseList = new ArrayList<>();
        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }
        return responseList;
    }

    @Override
    public List<FoodResponse> getLowStockFoods() {
        List<Food> foods = foodRepository.findByQuantityLessThan(10);
        List<FoodResponse> responseList = new ArrayList<>();
        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }
        return responseList;
    }

    @Override
    public List<FoodResponse> getFoodsByBranch(
            Long organizationId,
            Long branchId) {

        List<Food> foods =
                foodRepository.getFoods(
                        organizationId,
                        branchId
                );

        List<FoodResponse> responseList = new ArrayList<>();

        for (Food food : foods) {
            responseList.add(mapToResponse(food, "Success"));
        }

        return responseList;
    }
}