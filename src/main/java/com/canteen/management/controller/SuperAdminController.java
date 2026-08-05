package com.canteen.management.controller;

import com.canteen.management.entity.Branch;
import com.canteen.management.entity.Organization;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/superadmin")
@CrossOrigin("*")
public class SuperAdminController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping("/organization/create")
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationRepository.save(organization);
    }

    @PostMapping("/branch/create")
    public ResponseEntity<Branch> createBranch(@RequestParam Long organizationId, @RequestBody Branch branch) {
        return organizationRepository.findById(organizationId)
                .map(org -> {
                    branch.setOrganization(org);
                    Branch saved = branchRepository.save(branch);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/organization/status")
    public ResponseEntity<Organization> updateOrganizationStatus(@RequestParam Long id, @RequestParam String status) {
        return organizationRepository.findById(id)
                .map(org -> {
                    org.setStatus(status);
                    Organization updated = organizationRepository.save(org);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }
}
