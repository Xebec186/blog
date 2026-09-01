package com.xebec.blog.service;

import com.xebec.blog.dto.CreatePostRequest;
import com.xebec.blog.dto.PostDto;
import com.xebec.blog.dto.UpdatePostRequest;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest, UUID userId);
    PostDto getPost(UUID id);
    List<PostDto> getAllPosts(UUID categoryId, UUID tagId);
    List<PostDto> getUserDraftPosts(UUID userId);
    PostDto updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
