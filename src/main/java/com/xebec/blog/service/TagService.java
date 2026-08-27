package com.xebec.blog.service;

import com.xebec.blog.dto.CreateTagRequest;
import com.xebec.blog.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(CreateTagRequest createTagRequest);
    List<TagDto> getAllTags();
}
