package com.canteen.management.controller;

import com.canteen.management.dto.ReviewRequest;
import com.canteen.management.dto.ReviewResponse;
import com.canteen.management.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ReviewResponse addReview(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }

    @GetMapping("/food/{foodId}")
    public List<ReviewResponse> getReviewsByFood(@PathVariable Long foodId) {
        return reviewService.getReviewsByFood(foodId);
    }
}