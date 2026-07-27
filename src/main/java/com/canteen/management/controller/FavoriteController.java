package com.canteen.management.controller;

import com.canteen.management.dto.FavoriteRequest;
import com.canteen.management.dto.FavoriteResponse;
import com.canteen.management.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@CrossOrigin("*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public FavoriteResponse addFavorite(
            @RequestBody FavoriteRequest request) {

        return favoriteService.addFavorite(request);
    }

    @PostMapping("/remove")
    public void removeFavorite(
            @RequestBody FavoriteRequest request) {

        favoriteService.removeFavorite(request);
    }

    @GetMapping("/{studentId}")
    public List<FavoriteResponse> getFavorites(
            @PathVariable String studentId) {

        return favoriteService.getFavorites(studentId);
    }
}