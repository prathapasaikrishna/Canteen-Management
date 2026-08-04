package com.canteen.management.dto;

import lombok.Data;

@Data
public class BranchRequest {

    private String branchCode;
    private String branchName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String phone;
    private String email;
    private String logoUrl;

    private Long organizationId;

}