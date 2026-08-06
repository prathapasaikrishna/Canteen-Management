package com.canteen.management.controller;

import com.canteen.management.dto.OrganizationRequest;
import com.canteen.management.dto.OrganizationResponse;
import com.canteen.management.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@CrossOrigin("*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/add")
    public OrganizationResponse addOrganization(
            @RequestBody OrganizationRequest request) {

        System.out.println(request);

        return organizationService.addOrganization(request);
    }

    @GetMapping("/all")
    public List<OrganizationResponse> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

}