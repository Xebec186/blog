package com.xebec.blog.service;

import com.xebec.blog.dto.CreateTagsRequest;
import com.xebec.blog.dto.TagDto;

import java.util.List;
import java.util.UUID;

public interface TagService {
    List<TagDto> createTags(CreateTagsRequest createTagsRequest);
    List<TagDto> getAllTags();
    void deleteTag(UUID id);
}
