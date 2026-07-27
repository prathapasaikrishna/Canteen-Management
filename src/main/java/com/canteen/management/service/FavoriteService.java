package com.canteen.management.service;

import com.canteen.management.dto.FavoriteRequest;
import com.canteen.management.dto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    FavoriteResponse addFavorite(FavoriteRequest request);

    void removeFavorite(FavoriteRequest request);

    List<FavoriteResponse> getFavorites(String studentId);

}