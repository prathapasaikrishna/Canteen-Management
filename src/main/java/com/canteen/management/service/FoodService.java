package com.canteen.management.service;

import java.util.List;

import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {

    FoodResponse addFood(FoodRequest foodRequest, MultipartFile image);

    List<FoodResponse> getAllFoods();

    FoodResponse getFoodById(Long id);

    FoodResponse updateFood(Long id, FoodRequest foodRequest);

    void deleteFood(Long id);

    List<FoodResponse> searchFood(String foodName);

    List<FoodResponse> getFoodByCategory(String category);

    List<FoodResponse> getTodayMenu();

    List<FoodResponse> getLowStockFoods();


}