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
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

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
            @RequestPart(value = "image", required = false) MultipartFile image
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

    @PutMapping("/status/{id}")
    public ResponseEntity<FoodResponse> updateFoodStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        FoodResponse res = foodService.updateFoodStatus(id, status);
        return ResponseEntity.ok(res);
    }

    @Autowired(required = false)
    private javax.sql.DataSource dataSource;

    @GetMapping("/debug-db")
    public ResponseEntity<String> debugDb() {
        StringBuilder sb = new StringBuilder();
        if (dataSource == null) {
            return ResponseEntity.ok("DataSource bean is NULL!");
        }
        try (java.sql.Connection conn = dataSource.getConnection()) {
            sb.append("Database Connection: SUCCESS\n");
            sb.append("Database Product Name: ").append(conn.getMetaData().getDatabaseProductName()).append("\n");
            sb.append("Database Product Version: ").append(conn.getMetaData().getDatabaseProductVersion()).append("\n");
            sb.append("Database URL: ").append(conn.getMetaData().getURL()).append("\n");

            // Try query
            try (java.sql.Statement stmt = conn.createStatement()) {
                sb.append("Executing SELECT 1 query...\n");
                stmt.executeQuery("SELECT 1");
                sb.append("SELECT 1: SUCCESS\n");
            } catch (Exception queryEx) {
                sb.append("SELECT 1 Query FAILED: ").append(queryEx.getMessage()).append("\n");
            }

            // List all tables
            sb.append("Listing all tables in database:\n");
            try (java.sql.ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"})) {
                boolean found = false;
                while (rs.next()) {
                    sb.append(" - ").append(rs.getString("TABLE_NAME")).append("\n");
                    found = true;
                }
                if (!found) {
                    sb.append(" No tables found in database!\n");
                }
            } catch (Exception tablesEx) {
                sb.append("Get tables FAILED: ").append(tablesEx.getMessage()).append("\n");
            }

        } catch (Exception e) {
            sb.append("Database Connection FAILED: ").append(e.getMessage()).append("\n");
            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));
            sb.append("\nStacktrace:\n").append(sw.toString());
        }
        return ResponseEntity.ok(sb.toString());
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