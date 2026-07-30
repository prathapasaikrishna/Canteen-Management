package com.canteen.management.controller;

import com.canteen.management.dto.RecommendationResponse;
import com.canteen.management.service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@CrossOrigin("*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{studentId}")
    public List<RecommendationResponse> getRecommendations(
            @PathVariable String studentId) {

        return recommendationService.getRecommendations(studentId);

    }
}