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

    @GetMapping("/{id}")
    public OrganizationResponse getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    @PutMapping("/update/{id}")
    public OrganizationResponse updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationRequest request) {

        return organizationService.updateOrganization(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrganization(@PathVariable Long id) {

        return organizationService.deleteOrganization(id);

    }

}