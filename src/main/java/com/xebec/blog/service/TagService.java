package com.xebec.blog.service;

import com.xebec.blog.dto.CreateTagsRequest;
import com.xebec.blog.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> createTags(CreateTagsRequest createTagsRequest);
    List<TagDto> getAllTags();
}
