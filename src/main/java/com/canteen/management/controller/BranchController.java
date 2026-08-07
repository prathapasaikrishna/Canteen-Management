package com.canteen.management.controller;

import com.canteen.management.dto.BranchRequest;
import com.canteen.management.dto.BranchResponse;
import com.canteen.management.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@CrossOrigin("*")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/add")
    public BranchResponse addBranch(
            @RequestBody BranchRequest request) {

        return branchService.addBranch(request);

    }

    @GetMapping("/all")
    public List<BranchResponse> getAllBranches() {

        return branchService.getAllBranches();

    }

    @GetMapping("/organization/{organizationId}")
    public List<BranchResponse> getBranchesByOrganization(
            @PathVariable Long organizationId) {

        return branchService.getBranchesByOrganization(
                organizationId);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteBranch(@PathVariable Long id) {
        return branchService.deleteBranch(id);
    }
}