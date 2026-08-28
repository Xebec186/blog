package com.xebec.blog.service;

import com.xebec.blog.dto.PostDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostDto> getAllPosts(UUID categoryId, UUID tagId);
}
