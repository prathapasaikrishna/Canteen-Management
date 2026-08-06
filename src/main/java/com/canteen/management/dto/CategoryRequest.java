package com.canteen.management.dto;

import lombok.Data;

@Data
public class CategoryRequest {

    private Long organizationId;

    private Long branchId;

    private String categoryName;

    private String categoryCode;

    private String description;

    private String imageUrl;
}