package com.xebec.blog.service;

import com.xebec.blog.dto.CategoryDto;
import com.xebec.blog.dto.CreateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);
    List<CategoryDto> getAllCategories();
    void deleteCategory(UUID id);
}
