package com.canteen.management.service;

import com.canteen.management.dto.ReviewRequest;
import com.canteen.management.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(ReviewRequest request);

    List<ReviewResponse> getReviewsByFood(Long foodId);

}