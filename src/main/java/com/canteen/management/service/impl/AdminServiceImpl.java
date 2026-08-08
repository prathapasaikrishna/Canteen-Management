package com.canteen.management.service.impl;

import com.canteen.management.dto.AdminDashboardResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.dto.DashboardResponse;
import com.canteen.management.service.AdminService;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public DashboardResponse getDashboard(String canteenId) {
        DashboardResponse response = new DashboardResponse();
        if (canteenId != null && !canteenId.trim().isEmpty()) {
            Long totalStudents = studentRepository.count();
            Long totalFoods = foodRepository.countByCanteenId(canteenId);
            Long totalOrders = orderRepository.countByCanteenId(canteenId);
            List<com.canteen.management.entity.Order> canteenOrders =
                    orderRepository.findByCanteenId(canteenId);
            Long totalPayments = (long) canteenOrders.size();
            Double totalRevenue = canteenOrders.stream()
                    .mapToDouble(order -> order.getTotalPrice() != null ? order.getTotalPrice() : 0.0)
                    .sum();

            response.setTotalStudents(totalStudents);
            response.setTotalFoods(totalFoods);
            response.setTotalOrders(totalOrders);
            response.setTotalPayments(totalPayments);
            response.setTotalRevenue(totalRevenue);
        } else {
            Long totalStudents = studentRepository.count();
            Long totalFoods = foodRepository.count();
            Long totalOrders = orderRepository.count();
            Long totalPayments = paymentRepository.count();
            Double totalRevenue = paymentRepository.findAll()
                    .stream()
                    .mapToDouble(payment -> payment.getAmount() != null ? payment.getAmount() : 0.0)
                    .sum();

            response.setTotalStudents(totalStudents);
            response.setTotalFoods(totalFoods);
            response.setTotalOrders(totalOrders);
            response.setTotalPayments(totalPayments);
            response.setTotalRevenue(totalRevenue);
        }

        // ---------- Most Ordered Food ----------
        List<Long> mostOrderedFoodIds = orderRepository.getMostOrderedFoodIds();
        if (!mostOrderedFoodIds.isEmpty()) {
            Long foodId = mostOrderedFoodIds.get(0);
            Optional<Food> food = foodRepository.findById(foodId);
            if (food.isPresent()) {
                response.setMostOrderedFood(food.get().getFoodName());
                response.setMostOrderedCount(
                        orderRepository.countByFoodId(foodId)
                );
            }
        } else {
            response.setMostOrderedFood("-");
            response.setMostOrderedCount(0L);
        }

        // ---------- Top Rated Food ----------
        List<Long> topRatedFoodIds = reviewRepository.getTopRatedFoodIds();
        if (!topRatedFoodIds.isEmpty()) {
            Long foodId = topRatedFoodIds.get(0);
            Optional<Food> food = foodRepository.findById(foodId);
            if (food.isPresent()) {
                response.setTopRatedFood(food.get().getFoodName());
                Double rating = reviewRepository.getAverageRating(foodId);
                if (rating == null) {
                    rating = 0.0;
                }
                response.setTopRating(
                        Math.round(rating * 10.0) / 10.0
                );
            }
        } else {
            response.setTopRatedFood("-");
            response.setTopRating(0.0);
        }

        return response;
    }

    @Override
    public DashboardResponse getFilteredDashboard(String canteenId, String filter) {
        DashboardResponse response = new DashboardResponse();
        
        // Base stats
        Long totalStudents = studentRepository.count();
        Long totalFoods = (canteenId != null && !canteenId.trim().isEmpty())
                ? foodRepository.countByCanteenId(canteenId)
                : foodRepository.count();

        response.setTotalStudents(totalStudents);
        response.setTotalFoods(totalFoods);

        // Fetch orders
        List<com.canteen.management.entity.Order> orders;
        if (canteenId != null && !canteenId.trim().isEmpty()) {
            orders = orderRepository.findByCanteenId(canteenId);
        } else {
            orders = orderRepository.findAll();
        }

        // Apply date-based filtering
        LocalDate now = LocalDate.now();
        LocalDate today = now;
        LocalDate weekAgo = now.minusDays(7);
        LocalDate monthAgo = now.minusDays(30);

        List<com.canteen.management.entity.Order> filteredOrders = new ArrayList<>();
        for (com.canteen.management.entity.Order order : orders) {
            String dateStr = order.getOrderDate();
            if (dateStr == null || dateStr.trim().isEmpty()) {
                continue;
            }
            try {
                LocalDate oDate = LocalDate.parse(dateStr.trim());
                boolean keep = false;
                if ("TODAY".equalsIgnoreCase(filter)) {
                    keep = oDate.isEqual(today);
                } else if ("WEEK".equalsIgnoreCase(filter)) {
                    keep = !oDate.isBefore(weekAgo) && !oDate.isAfter(today);
                } else if ("MONTH".equalsIgnoreCase(filter)) {
                    keep = !oDate.isBefore(monthAgo) && !oDate.isAfter(today);
                } else {
                    keep = true;
                }
                if (keep) {
                    filteredOrders.add(order);
                }
            } catch (Exception e) {
                if ("TODAY".equalsIgnoreCase(filter) && dateStr.equals(today.toString())) {
                    filteredOrders.add(order);
                } else if (!"TODAY".equalsIgnoreCase(filter) && !"WEEK".equalsIgnoreCase(filter) && !"MONTH".equalsIgnoreCase(filter)) {
                    filteredOrders.add(order);
                }
            }
        }

        // Aggregate statistics from filtered list
        Long totalOrders = (long) filteredOrders.size();
        Long totalPayments = (long) filteredOrders.size();
        Double totalRevenue = filteredOrders.stream()
                .mapToDouble(order -> order.getTotalPrice() != null ? order.getTotalPrice() : 0.0)
                .sum();

        response.setTotalOrders(totalOrders);
        response.setTotalPayments(totalPayments);
        response.setTotalRevenue(totalRevenue);

        // ---------- Filtered Most Ordered Food ----------
        Map<Long, Long> foodCountMap = new HashMap<>();
        for (com.canteen.management.entity.Order order : filteredOrders) {
            Long fId = order.getFoodId();
            if (fId != null) {
                foodCountMap.put(fId, foodCountMap.getOrDefault(fId, 0L) + 1L);
            }
        }

        Long mostOrderedFoodId = null;
        Long maxCount = 0L;
        for (Map.Entry<Long, Long> entry : foodCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrderedFoodId = entry.getKey();
            }
        }

        if (mostOrderedFoodId != null) {
            Optional<Food> food = foodRepository.findById(mostOrderedFoodId);
            if (food.isPresent()) {
                response.setMostOrderedFood(food.get().getFoodName());
                response.setMostOrderedCount(maxCount);
            }
        } else {
            response.setMostOrderedFood("-");
            response.setMostOrderedCount(0L);
        }

        // ---------- Top Rated Food (General) ----------
        List<Long> topRatedFoodIds = reviewRepository.getTopRatedFoodIds();
        if (!topRatedFoodIds.isEmpty()) {
            Long foodId = topRatedFoodIds.get(0);
            Optional<Food> food = foodRepository.findById(foodId);
            if (food.isPresent()) {
                response.setTopRatedFood(food.get().getFoodName());
                Double rating = reviewRepository.getAverageRating(foodId);
                if (rating == null) {
                    rating = 0.0;
                }
                response.setTopRating(
                        Math.round(rating * 10.0) / 10.0
                );
            }
        } else {
            response.setTopRatedFood("-");
            response.setTopRating(0.0);
        }

        return response;
    }

    @Override
    public AdminDashboardResponse getBranchDashboard(Long branchId){

        AdminDashboardResponse response = new AdminDashboardResponse();

        response.setTotalOrders(
                orderRepository.countByBranchId(branchId)
        );

        response.setPendingOrders(
                orderRepository.countByBranchIdAndOrderStatus(
                        branchId,
                        "PLACED"
                )
        );

        response.setCompletedOrders(
                orderRepository.countByBranchIdAndOrderStatus(
                        branchId,
                        "COLLECTED"
                )
        );

        response.setTotalFoods(
                foodRepository.countByBranchId(branchId)
        );

        response.setTotalStudents(
                studentRepository.countByBranchId(branchId)
        );

        response.setTotalRevenue(
                orderRepository.getTotalRevenue(branchId)
        );

        return response;
    }

    @Override
    public DashboardResponse getDashboardByBranch(Long branchId) {
        DashboardResponse response = new DashboardResponse();
        if (branchId == null) {
            return response;
        }

        java.time.LocalDate branchCreatedDate = null;
        java.util.Optional<com.canteen.management.entity.Branch> branchOpt = branchRepository.findById(branchId);
        if (branchOpt.isPresent() && branchOpt.get().getCreatedAt() != null) {
            branchCreatedDate = branchOpt.get().getCreatedAt().toLocalDate();
        }

        Long totalStudents = studentRepository.countByBranchIdAndRole(branchId, "STUDENT");
        if (totalStudents == null || totalStudents == 0) {
            totalStudents = studentRepository.countByBranchId(branchId);
        }
        Long totalFoods = foodRepository.countByBranchId(branchId);

        List<com.canteen.management.entity.Order> allOrders = orderRepository.findByBranchId(branchId);
        List<com.canteen.management.entity.Order> filteredOrders = new ArrayList<>();
        double totalRevenue = 0.0;
        for (com.canteen.management.entity.Order o : allOrders) {
            if (branchCreatedDate != null && o.getOrderDate() != null) {
                try {
                    java.time.LocalDate oDate = java.time.LocalDate.parse(o.getOrderDate().trim());
                    if (oDate.isBefore(branchCreatedDate)) {
                        continue;
                    }
                } catch (Exception ignored) {}
            }
            filteredOrders.add(o);
            totalRevenue += (o.getTotalPrice() != null ? o.getTotalPrice() : 0.0);
        }

        response.setTotalStudents(totalStudents);
        response.setTotalFoods(totalFoods);
        response.setTotalOrders((long) filteredOrders.size());
        response.setTotalPayments((long) filteredOrders.size());
        response.setTotalRevenue(totalRevenue);

        // ---------- Most Ordered Food ----------
        Map<Long, Long> foodOrderCount = new java.util.HashMap<>();
        for (com.canteen.management.entity.Order o : filteredOrders) {
            if (o.getFoodId() != null) {
                int qty = o.getQuantity() != null ? o.getQuantity() : 1;
                foodOrderCount.put(o.getFoodId(), foodOrderCount.getOrDefault(o.getFoodId(), 0L) + qty);
            }
        }
        Long mostOrderedFoodId = null;
        Long maxCount = 0L;
        for (Map.Entry<Long, Long> entry : foodOrderCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrderedFoodId = entry.getKey();
            }
        }
        String mostOrderedFoodName = "N/A";
        if (mostOrderedFoodId != null) {
            Optional<Food> f = foodRepository.findById(mostOrderedFoodId);
            if (f.isPresent()) {
                mostOrderedFoodName = f.get().getFoodName();
            }
        }
        response.setMostOrderedFood(mostOrderedFoodName);
        response.setMostOrderedCount(maxCount);

        response.setTopRatedFood("N/A");
        response.setTopRating(0.0);

        return response;
    }
}