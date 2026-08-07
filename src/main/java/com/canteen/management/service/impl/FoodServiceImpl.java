package com.canteen.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.canteen.management.entity.Category;
import com.canteen.management.repository.*;
import com.canteen.management.service.CloudinaryService;
import com.canteen.management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.dto.FoodRequest;
import com.canteen.management.dto.FoodResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.service.FoodService;
import org.springframework.web.multipart.MultipartFile;
import com.canteen.management.entity.Organization;
import com.canteen.management.entity.Branch;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private FoodResponse mapToResponse(Food food, String message) {


        String organizationName = "";

        String branchName = "";


        if(food.getOrganizationId() != null){

            Organization organization =
                    organizationRepository.findById(food.getOrganizationId())
                            .orElse(null);


            if(organization != null){
                organizationName = organization.getName();
            }
        }


        if(food.getBranchId() != null){

            Branch branch =
                    branchRepository.findById(food.getBranchId())
                            .orElse(null);


            if(branch != null){
                branchName = branch.getBranchName();
            }
        }
        Double avgRating = reviewRepository.getAverageRating(food.getId());
        Long totalRev = reviewRepository.countByFoodId(food.getId());
        
        double ratingVal = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;
        long revCount = totalRev != null ? totalRev : 0L;

        return new FoodResponse(
                food.getId(),
                food.getFoodName(),
                food.getCategory() != null
                        ? food.getCategory().getCategoryName()
                        : null,
                food.getPrice(),
                food.getImageUrl(),
                food.getAvailableDate(),
                food.getAvailableTime(),
                food.getQuantity(),
                food.getStatus(),
                food.getCanteenId(),
                message,
                ratingVal,
                revCount,
                food.getOrganizationId(),
                organizationName,

                food.getBranchId(),
                branchName
        );
    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile image) {
        try {
            System.out.println("STEP 1");

            String imageUrl = "";
            if (image != null && !image.isEmpty()) {
                imageUrl = cloudinaryService.uploadImage(image);
            }

            System.out.println("STEP 2");

            Food food = new Food();

            System.out.println("STEP 3");

            food.setFoodName(foodRequest.getFoodName());

            System.out.println("STEP 4");

            Category category = null;
            if (foodRequest.getCategoryId() != null) {
                category = categoryRepository.findById(foodRequest.getCategoryId()).orElse(null);
            }
            if (category == null && foodRequest.getCategory() != null && !foodRequest.getCategory().trim().isEmpty()) {
                String catName = foodRequest.getCategory().trim();
                // Search globally case-insensitively to reuse
                List<Category> globalOrgs = categoryRepository.findByCategoryNameIgnoreCase(catName);
                if (!globalOrgs.isEmpty()) {
                    category = globalOrgs.get(0);
                } else {
                    // Create a unique-safe new category
                    category = new Category();
                    category.setCategoryName(catName);
                    category.setCategoryCode(catName.toUpperCase().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis());
                    if (foodRequest.getBranchId() != null) {
                        Branch branch = branchRepository.findById(foodRequest.getBranchId()).orElse(null);
                        category.setBranch(branch);
                    }
                    if (foodRequest.getOrganizationId() != null) {
                        Organization org = organizationRepository.findById(foodRequest.getOrganizationId()).orElse(null);
                        category.setOrganization(org);
                    }
                    category = categoryRepository.save(category);
                }
            }
            if (category == null) {
                String defaultCat = "General";
                List<Category> existing = categoryRepository.findByCategoryNameIgnoreCase(defaultCat);
                if (!existing.isEmpty()) {
                    category = existing.get(0);
                } else {
                    category = new Category();
                    category.setCategoryName(defaultCat);
                    category.setCategoryCode("GENERAL_" + System.currentTimeMillis());
                    category = categoryRepository.save(category);
                }
            }

            System.out.println("STEP 5");

            food.setCategory(category);

            System.out.println("STEP 6");

            food.setPrice(foodRequest.getPrice());

            System.out.println("STEP 7");

            food.setCategory(category);
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
            System.out.println("STEP 8");
            Food savedFood = foodRepository.save(food);

            System.out.println("STEP 9");

            try {
                notificationService.notifyBranchStudents(
                        savedFood.getBranchId(),
                        "🍔 New Food Available",
                        savedFood.getFoodName() + " is Available Today."
                );
            } catch (Exception e) {
                System.err.println("FCM Notification failed but food is saved: " + e.getMessage());
            }

            System.out.println("STEP 10");

            return mapToResponse(savedFood, "Food Added Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Food addition failed: " + ex.getMessage(), ex);
        }
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

        if (!food.getOrganizationId().equals(foodRequest.getOrganizationId())
                || !food.getBranchId().equals(foodRequest.getBranchId())) {

            throw new RuntimeException("You cannot edit another branch food.");

        }

        food.setFoodName(foodRequest.getFoodName());
        food.setCategory(foodRequest.getCategory());
        food.setPrice(foodRequest.getPrice());
        food.setAvailableDate(foodRequest.getAvailableDate());
        food.setAvailableTime(foodRequest.getAvailableTime());
        food.setQuantity(foodRequest.getQuantity());
        food.setStatus(foodRequest.getStatus());
        food.setCanteenId(foodRequest.getCanteenId());

        if (image != null && !image.isEmpty()) {
            String uploadedUrl = cloudinaryService.uploadImage(image);
            if (uploadedUrl != null && !uploadedUrl.isEmpty()) {
                food.setImageUrl(uploadedUrl);
            }
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
        List<Food> foods =
                foodRepository.findByCategory_CategoryNameIgnoreCase(category);
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
                foodRepository.findByOrganizationIdAndBranchId(
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