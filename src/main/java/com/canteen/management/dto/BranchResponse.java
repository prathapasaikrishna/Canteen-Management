package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponse {

    private Long id;
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
    private String status;

    private Long organizationId;
    private String organizationName;

}