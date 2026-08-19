package com.xebec.blog.service;

import com.xebec.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
}
