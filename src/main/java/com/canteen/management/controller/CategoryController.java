package com.canteen.management.controller;

import com.canteen.management.dto.CategoryRequest;
import com.canteen.management.dto.CategoryResponse;
import com.canteen.management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}