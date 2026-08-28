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
import java.util.Optional;
import java.util.UUID;

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

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            if(!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete category with posts");
            }
            categoryRepository.deleteById(id);
        }
    }

}
