package com.canteen.management.service;

import com.canteen.management.dto.RecommendationResponse;
import java.util.List;

public interface RecommendationService {

    List<RecommendationResponse> getRecommendations(String studentId);

}