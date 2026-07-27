package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteResponse {

    private Integer id;

    private String studentId;

    private Long foodId;

}