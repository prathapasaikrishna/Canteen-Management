package com.canteen.management.dto;

import lombok.Data;

@Data
public class CategoryResponse {

    private Long id;

    private Long organizationId;

    private String organizationName;

    private Long branchId;

    private String branchName;

    private String categoryName;

    private String categoryCode;

    private String description;

    private String imageUrl;

    private String status;
}