package com.canteen.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import com.canteen.management.service.FoodService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public FoodResponse addFood(@Valid @RequestBody FoodRequest foodRequest) {

        return foodService.addFood(foodRequest);
    }

    @GetMapping("/all")
    public List<FoodResponse> getAllFoods() {

        return foodService.getAllFoods();

    }

    @GetMapping("/{id}")
    public FoodResponse getFoodById(@PathVariable Long id) {

        return foodService.getFoodById(id);
    }

    @PutMapping("/update/{id}")
    public FoodResponse updateFood(
            @PathVariable Long id,
            @Valid @RequestBody FoodRequest foodRequest) {

        return foodService.updateFood(id, foodRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFood(@PathVariable Long id) {

        foodService.deleteFood(id);

        return "Food Deleted Successfully";
    }

    @GetMapping("/search/{foodName}")
    public List<FoodResponse> searchFood(@PathVariable String foodName) {

        return foodService.searchFood(foodName);
    }

    @GetMapping("/category/{category}")
    public List<FoodResponse> getFoodByCategory(
            @PathVariable String category) {

        return foodService.getFoodByCategory(category);
    }

    @GetMapping("/today")
    public List<FoodResponse> getTodayMenu() {

        return foodService.getTodayMenu();
    }
    @GetMapping("/low-stock")
    public List<FoodResponse> getLowStockFoods() {

        return foodService.getLowStockFoods();
    }
}