package com.canteen.management.controller;

import com.canteen.management.dto.AnalyticsResponse;
import com.canteen.management.service.impl.AnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@CrossOrigin("*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/{canteenId}")
    public AnalyticsResponse getAnalytics(
            @PathVariable String canteenId) {

        return analyticsService.getAnalytics(canteenId);
    }
}