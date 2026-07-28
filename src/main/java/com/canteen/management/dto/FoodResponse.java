package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

    private Long id;
    private String foodName;
    private String category;
    private Double price;
    private String availableDate;
    private String availableTime;
    private Integer quantity;
    private String status;
    private String canteenId;
    private String message;
    private String imageUrl;

}

