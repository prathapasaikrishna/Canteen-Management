package com.canteen.management.service;

import com.canteen.management.dto.CategoryRequest;
import com.canteen.management.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    String deleteCategory(Long id);

    List<CategoryResponse> getCategoriesByBranch(Long branchId);

}