package com.canteen.management.service.impl;

import com.canteen.management.dto.AnalyticsResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.entity.Order;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    public AnalyticsResponse getAnalytics(String canteenId) {

        AnalyticsResponse response = new AnalyticsResponse();

        List<Order> orders = orderRepository.findAll();
        List<Food> foods = foodRepository.findAll();

        LocalDate today = LocalDate.now();

        double todayRevenue = 0;
        double weeklyRevenue = 0;
        double monthlyRevenue = 0;

        int todayOrders = 0;
        int weeklyOrders = 0;
        int monthlyOrders = 0;

        // Orders Analytics
        for (Order order : orders) {

            if (order.getCanteenId() == null ||
                    !order.getCanteenId().equalsIgnoreCase(canteenId)) {
                continue;
            }

            if (order.getOrderDate() == null) {
                continue;
            }

            try {

                LocalDate orderDate = LocalDate.parse(
                        order.getOrderDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                );

                if (orderDate.equals(today)) {
                    todayRevenue += order.getTotalPrice();
                    todayOrders++;
                }

                if (!orderDate.isBefore(today.minusDays(6))) {
                    weeklyRevenue += order.getTotalPrice();
                    weeklyOrders++;
                }

                if (orderDate.getMonthValue() == today.getMonthValue()
                        && orderDate.getYear() == today.getYear()) {

                    monthlyRevenue += order.getTotalPrice();
                    monthlyOrders++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Food Analytics
        int lowStock = 0;
        int soldOut = 0;

        for (Food food : foods) {

            if (food.getCanteenId() == null ||
                    !food.getCanteenId().equalsIgnoreCase(canteenId)) {
                continue;
            }

            if (food.getQuantity() <= 0) {

                soldOut++;

            } else if (food.getQuantity() <= 10) {

                lowStock++;
            }
        }

        response.setTodayRevenue(todayRevenue);
        response.setWeeklyRevenue(weeklyRevenue);
        response.setMonthlyRevenue(monthlyRevenue);

        response.setTodayOrders(todayOrders);
        response.setWeeklyOrders(weeklyOrders);
        response.setMonthlyOrders(monthlyOrders);

        response.setLowStockFoods(lowStock);
        response.setSoldOutFoods(soldOut);

        return response;
    }
}