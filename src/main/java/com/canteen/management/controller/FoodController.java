package com.canteen.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import com.canteen.management.service.FoodService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FoodResponse addFood(
            @RequestPart("food") String foodJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        com.fasterxml.jackson.databind.ObjectMapper mapper =
                new com.fasterxml.jackson.databind.ObjectMapper();

        FoodRequest foodRequest =
                mapper.readValue(foodJson, FoodRequest.class);

        return foodService.addFood(foodRequest, image);
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