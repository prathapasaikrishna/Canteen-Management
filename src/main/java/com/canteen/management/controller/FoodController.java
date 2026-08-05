package com.canteen.management.controller;

import java.util.List;

import com.canteen.management.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import com.canteen.management.service.FoodService;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FoodResponse addFood(
            @RequestPart("food") String foodJson,
            @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {

        com.fasterxml.jackson.databind.ObjectMapper mapper =
                new com.fasterxml.jackson.databind.ObjectMapper()
                .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
    @PostMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable Long id,
            @RequestPart("food") String foodJson,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws Exception {

        System.out.println("==================================================");
        System.out.println("UPDATE FOOD ENDPOINT RECEIVED");
        System.out.println("Food ID: " + id);
        System.out.println("Food JSON: " + foodJson);
        System.out.println("Image Received: " + (image != null ? image.getOriginalFilename() + " (" + image.getSize() + " bytes)" : "NULL"));
        System.out.println("==================================================");

        com.fasterxml.jackson.databind.ObjectMapper mapper =
                new com.fasterxml.jackson.databind.ObjectMapper()
                .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Food food = mapper.readValue(foodJson, Food.class);

        FoodResponse updatedFood = foodService.updateFood(id, food, image);

        return ResponseEntity.ok(updatedFood);
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

    @GetMapping("/branch")
    public List<FoodResponse> getFoodsByBranch(

            @RequestParam Long organizationId,

            @RequestParam Long branchId

    ) {

        return foodService.getFoodsByBranch(
                organizationId,
                branchId
        );

    }

    @Autowired
    private com.cloudinary.Cloudinary cloudinary;

    @PostMapping(value = "/test-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testUpload(
            @RequestParam("image") MultipartFile image
    ) {
        try {
            java.util.Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    image.getBytes(),
                    com.cloudinary.utils.ObjectUtils.emptyMap()
            );
            return ResponseEntity.ok("Success: " + uploadResult.get("secure_url").toString());
        } catch (Exception e) {
            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));
            return ResponseEntity.status(500).body("Error: " + e.getMessage() + "\n" + sw.toString());
        }
    }

    @GetMapping("/debug-env")
    public ResponseEntity<String> debugEnv() {
        StringBuilder sb = new StringBuilder();
        sb.append("CLOUDINARY_URL exists: ").append(System.getenv("CLOUDINARY_URL") != null).append("\n");
        if (System.getenv("CLOUDINARY_URL") != null) {
            String url = System.getenv("CLOUDINARY_URL");
            sb.append("CLOUDINARY_URL value: ").append(url.replaceAll(":[^@]+@", ":***@")).append("\n");
        }
        sb.append("CLOUDINARY_CLOUD_NAME exists: ").append(System.getenv("CLOUDINARY_CLOUD_NAME") != null).append("\n");
        sb.append("CLOUDINARY_CLOUD_NAME value: ").append(System.getenv("CLOUDINARY_CLOUD_NAME")).append("\n");
        sb.append("CLOUDINARY_API_KEY exists: ").append(System.getenv("CLOUDINARY_API_KEY") != null).append("\n");
        sb.append("CLOUDINARY_API_KEY value: ").append(System.getenv("CLOUDINARY_API_KEY")).append("\n");
        return ResponseEntity.ok(sb.toString());
    }
}