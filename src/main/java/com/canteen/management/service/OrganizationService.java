package com.canteen.management.service;

import com.canteen.management.dto.OrganizationRequest;
import com.canteen.management.dto.OrganizationResponse;

import java.util.List;

public interface OrganizationService {

    OrganizationResponse addOrganization(OrganizationRequest request);

    List<OrganizationResponse> getAllOrganizations();

    OrganizationResponse getOrganizationById(Long id);

    OrganizationResponse updateOrganization(Long id,
                                            OrganizationRequest request);

    String deleteOrganization(Long id);

}