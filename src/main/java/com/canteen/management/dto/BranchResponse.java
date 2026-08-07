package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    private Double revenue;

    public BranchResponse(Long id, String branchCode, String branchName, String address, String city, String state, String country, String pincode, String phone, String email, String logoUrl, String status, Long organizationId, String organizationName) {
        this.id = id;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.phone = phone;
        this.email = email;
        this.logoUrl = logoUrl;
        this.status = status;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }
}