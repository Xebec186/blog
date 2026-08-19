package com.xebec.blog.service.impl;

import com.xebec.blog.dto.CategoryDto;
import com.xebec.blog.mapper.CategoryMapper;
import com.xebec.blog.repository.CategoryRepository;
import com.xebec.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllWithPostCount()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

}
