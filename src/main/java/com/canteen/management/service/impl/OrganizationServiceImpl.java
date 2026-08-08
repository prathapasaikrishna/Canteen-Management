package com.canteen.management.service.impl;

import com.canteen.management.dto.OrganizationRequest;
import com.canteen.management.dto.OrganizationResponse;
import com.canteen.management.entity.Organization;
import com.canteen.management.repository.OrganizationRepository;
import com.canteen.management.service.AuditLogService;
import com.canteen.management.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AuditLogService auditLogService;

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

        auditLogService.saveLog(
                "SUPER_ADMIN",
                "SUPER_ADMIN",
                "CREATE_ORGANIZATION",
                "Organization Created : " + savedOrganization.getName(),
                savedOrganization.getId(),
                null
        );

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

        return organizationRepository.findAll()
                .stream()
                .filter(org -> !"INACTIVE".equalsIgnoreCase(org.getStatus()))
                .map(org -> new OrganizationResponse(
                        org.getId(),
                        org.getOrganizationCode(),
                        org.getName(),
                        org.getType(),
                        org.getEmail(),
                        org.getPhone(),
                        org.getAddress(),
                        org.getCity(),
                        org.getState(),
                        org.getCountry(),
                        org.getPincode(),
                        org.getLogoUrl(),
                        org.getStatus()
                ))
                .toList();
    }


    @Override
    public OrganizationResponse getOrganizationById(Long id) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        return new OrganizationResponse(
                organization.getId(),
                organization.getOrganizationCode(),
                organization.getName(),
                organization.getType(),
                organization.getEmail(),
                organization.getPhone(),
                organization.getAddress(),
                organization.getCity(),
                organization.getState(),
                organization.getCountry(),
                organization.getPincode(),
                organization.getLogoUrl(),
                organization.getStatus()
        );
    }


    @Override
    public OrganizationResponse updateOrganization(Long id, OrganizationRequest request) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

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

        Organization updated = organizationRepository.save(organization);

        auditLogService.saveLog(
                "SUPER_ADMIN",
                "SUPER_ADMIN",
                "UPDATE_ORGANIZATION",
                "Organization Updated : " + updated.getName(),
                updated.getId(),
                null
        );



        return new OrganizationResponse(
                updated.getId(),
                updated.getOrganizationCode(),
                updated.getName(),
                updated.getType(),
                updated.getEmail(),
                updated.getPhone(),
                updated.getAddress(),
                updated.getCity(),
                updated.getState(),
                updated.getCountry(),
                updated.getPincode(),
                updated.getLogoUrl(),
                updated.getStatus()
        );
    }
    @Override
    public String deleteOrganization(Long id) {



        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        auditLogService.saveLog(
                "SUPER_ADMIN",
                "SUPER_ADMIN",
                "DELETE_ORGANIZATION",
                "Organization Deleted : " + organization.getName(),
                organization.getId(),
                null
        );


        organization.setStatus("INACTIVE");

        organizationRepository.save(organization);

        return "Organization deleted successfully";
    }
}