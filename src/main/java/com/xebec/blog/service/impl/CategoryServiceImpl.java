package com.xebec.blog.service.impl;

import com.xebec.blog.dto.CategoryDto;
import com.xebec.blog.dto.CreateCategoryRequest;
import com.xebec.blog.entity.Category;
import com.xebec.blog.mapper.CategoryMapper;
import com.xebec.blog.repository.CategoryRepository;
import com.xebec.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        String categoryName = createCategoryRequest.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)) {
            throw new IllegalArgumentException("Category already exists with name: "
                    + categoryName);
        }
        Category category = categoryMapper.toEntity(createCategoryRequest);

        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.toDto(createdCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllWithPosts()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

}
