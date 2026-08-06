package com.canteen.management.service.impl;

import com.canteen.management.dto.CategoryRequest;
import com.canteen.management.dto.CategoryResponse;
import com.canteen.management.entity.Branch;
import com.canteen.management.entity.Category;
import com.canteen.management.entity.Organization;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.CategoryRepository;
import com.canteen.management.repository.OrganizationRepository;
import com.canteen.management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {

        if (categoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            throw new RuntimeException("Category Code already exists");
        }

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        Category category = new Category();

        category.setOrganization(organization);
        category.setBranch(branch);
        category.setCategoryName(request.getCategoryName());
        category.setCategoryCode(request.getCategoryCode());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        Category saved = categoryRepository.save(category);

        CategoryResponse response = new CategoryResponse();

        response.setId(saved.getId());
        response.setOrganizationId(saved.getOrganization().getId());
        response.setOrganizationName(saved.getOrganization().getName());
        response.setBranchId(saved.getBranch().getId());
        response.setBranchName(saved.getBranch().getBranchName());
        response.setCategoryName(saved.getCategoryName());
        response.setCategoryCode(saved.getCategoryCode());
        response.setDescription(saved.getDescription());
        response.setImageUrl(saved.getImageUrl());
        response.setStatus(saved.getStatus());

        return response;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> {

                    CategoryResponse response = new CategoryResponse();

                    response.setId(category.getId());

                    response.setOrganizationId(category.getOrganization().getId());
                    response.setOrganizationName(category.getOrganization().getName());

                    response.setBranchId(category.getBranch().getId());
                    response.setBranchName(category.getBranch().getBranchName());

                    response.setCategoryName(category.getCategoryName());
                    response.setCategoryCode(category.getCategoryCode());
                    response.setDescription(category.getDescription());
                    response.setImageUrl(category.getImageUrl());
                    response.setStatus(category.getStatus());

                    return response;

                }).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setOrganizationId(category.getOrganization().getId());
        response.setOrganizationName(category.getOrganization().getName());

        response.setBranchId(category.getBranch().getId());
        response.setBranchName(category.getBranch().getBranchName());

        response.setCategoryName(category.getCategoryName());
        response.setCategoryCode(category.getCategoryCode());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImageUrl());
        response.setStatus(category.getStatus());

        return response;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        category.setOrganization(organization);
        category.setBranch(branch);
        category.setCategoryName(request.getCategoryName());
        category.setCategoryCode(request.getCategoryCode());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        Category updated = categoryRepository.save(category);

        CategoryResponse response = new CategoryResponse();

        response.setId(updated.getId());
        response.setOrganizationId(updated.getOrganization().getId());
        response.setOrganizationName(updated.getOrganization().getName());
        response.setBranchId(updated.getBranch().getId());
        response.setBranchName(updated.getBranch().getBranchName());
        response.setCategoryName(updated.getCategoryName());
        response.setCategoryCode(updated.getCategoryCode());
        response.setDescription(updated.getDescription());
        response.setImageUrl(updated.getImageUrl());
        response.setStatus(updated.getStatus());

        return response;
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);

        return "Category Deleted Successfully";
    }
    @Override
    public List<CategoryResponse> getCategoriesByBranch(Long branchId) {

        return categoryRepository.findByBranch_Id(branchId)
                .stream()
                .map(category -> {

                    CategoryResponse response = new CategoryResponse();

                    response.setId(category.getId());
                    response.setOrganizationId(category.getOrganization().getId());
                    response.setOrganizationName(category.getOrganization().getName());

                    response.setBranchId(category.getBranch().getId());
                    response.setBranchName(category.getBranch().getBranchName());

                    response.setCategoryName(category.getCategoryName());
                    response.setCategoryCode(category.getCategoryCode());
                    response.setDescription(category.getDescription());
                    response.setImageUrl(category.getImageUrl());
                    response.setStatus(category.getStatus());

                    return response;

                }).toList();
    }
}