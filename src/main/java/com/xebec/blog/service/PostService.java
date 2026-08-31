package com.xebec.blog.service;

import com.xebec.blog.dto.CreatePostRequest;
import com.xebec.blog.dto.PostDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest, UUID userId);
    List<PostDto> getAllPosts(UUID categoryId, UUID tagId);
    List<PostDto> getUserDraftPosts(UUID userId);
}
