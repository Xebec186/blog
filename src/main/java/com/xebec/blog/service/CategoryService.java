package com.xebec.blog.service;

import com.xebec.blog.dto.CategoryDto;
import com.xebec.blog.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);
    List<CategoryDto> getAllCategories();
}
