package com.canteen.management.controller;

import com.canteen.management.dto.CategoryRequest;
import com.canteen.management.dto.CategoryResponse;
import com.canteen.management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public CategoryResponse addCategory(@RequestBody CategoryRequest request) {

        return categoryService.addCategory(request);

    }

    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/branch/{branchId}")
    public List<CategoryResponse> getCategoriesByBranch(@PathVariable Long branchId) {

        return categoryService.getCategoriesByBranch(branchId);

    }
}