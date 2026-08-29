package com.xebec.blog.service.impl;

import com.xebec.blog.dto.PostDto;
import com.xebec.blog.enums.PostStatus;
import com.xebec.blog.mapper.PostMapper;
import com.xebec.blog.repository.PostRepository;
import com.xebec.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostDto> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null) {
           return postRepository.findAllByStatusAndCategoryIdAndTags_Id(PostStatus.PUBLISHED, categoryId, tagId).stream()
                   .map(postMapper::toDto)
                   .toList();
        }

        if(categoryId != null) {
            return postRepository.findAllByStatusAndCategoryId(PostStatus.PUBLISHED, categoryId).stream()
                    .map(postMapper::toDto)
                    .toList();
        }

        if(tagId != null) {
            return postRepository.findAllByStatusAndTags_Id(PostStatus.PUBLISHED, tagId).stream()
                    .map(postMapper::toDto)
                    .toList();
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getUserDraftPosts(UUID userId) {
         return postRepository.findAllByStatusAndAuthorId(PostStatus.DRAFT, userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }
}