package com.canteen.management.service.impl;

import com.canteen.management.dto.OrganizationRequest;
import com.canteen.management.dto.OrganizationResponse;
import com.canteen.management.entity.Organization;
import com.canteen.management.repository.OrganizationRepository;
import com.canteen.management.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse addOrganization(OrganizationRequest request) {

        if (organizationRepository.existsByOrganizationCode(
                request.getOrganizationCode())) {

            throw new RuntimeException("Organization Code already exists");
        }

        Organization organization = new Organization();

        organization.setOrganizationCode(request.getOrganizationCode());
        organization.setName(request.getName());
        organization.setType(request.getType());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());
        organization.setAddress(request.getAddress());
        organization.setCity(request.getCity());
        organization.setState(request.getState());
        organization.setCountry(request.getCountry());
        organization.setPincode(request.getPincode());
        organization.setLogoUrl(request.getLogoUrl());

        Organization savedOrganization =
                organizationRepository.save(organization);

        return new OrganizationResponse(
                savedOrganization.getId(),
                savedOrganization.getOrganizationCode(),
                savedOrganization.getName(),
                savedOrganization.getType(),
                savedOrganization.getEmail(),
                savedOrganization.getPhone(),
                savedOrganization.getAddress(),
                savedOrganization.getCity(),
                savedOrganization.getState(),
                savedOrganization.getCountry(),
                savedOrganization.getPincode(),
                savedOrganization.getLogoUrl(),
                savedOrganization.getStatus()
        );
    }

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        return null;
    }

    @Override
    public OrganizationResponse getOrganizationById(Long id) {
        return null;
    }

    @Override
    public OrganizationResponse updateOrganization(Long id,
                                                   OrganizationRequest request) {
        return null;
    }

    @Override
    public String deleteOrganization(Long id) {
        return null;
    }
}