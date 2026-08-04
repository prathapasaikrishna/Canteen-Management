package com.canteen.management.dto;

import lombok.Data;

@Data
public class OrganizationRequest {

    private String organizationCode;
    private String name;
    private String type;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String logoUrl;

}