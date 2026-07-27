package com.canteen.management.service.impl;

import com.canteen.management.dto.ReviewRequest;
import com.canteen.management.dto.ReviewResponse;
import com.canteen.management.entity.Review;
import com.canteen.management.repository.ReviewRepository;
import com.canteen.management.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public ReviewResponse addReview(ReviewRequest request) {

        Review review = new Review();

        review.setStudentId(request.getStudentId());
        review.setFoodId(request.getFoodId());
        review.setRating(request.getRating());
        review.setReview(request.getReview());
        review.setReviewDate(LocalDate.now().toString());


        Review savedReview = reviewRepository.save(review);


        return new ReviewResponse(

                savedReview.getId(),
                savedReview.getStudentId(),
                savedReview.getFoodId(),
                savedReview.getRating(),
                savedReview.getReview(),
                savedReview.getReviewDate(),
                "Review Added Successfully"

        );

    }



    @Override
    public List<ReviewResponse> getReviewsByFood(Long foodId) {


        List<Review> reviews =
                reviewRepository.findByFoodIdOrderByIdDesc(foodId);


        List<ReviewResponse> responseList =
                new ArrayList<>();


        for(Review review : reviews) {


            responseList.add(

                    new ReviewResponse(

                            review.getId(),
                            review.getStudentId(),
                            review.getFoodId(),
                            review.getRating(),
                            review.getReview(),
                            review.getReviewDate(),
                            "Success"

                    )

            );

        }


        return responseList;

    }

}