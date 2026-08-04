package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {

    private Long id;
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
    private String status;

}