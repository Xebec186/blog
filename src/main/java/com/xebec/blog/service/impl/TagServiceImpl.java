package com.xebec.blog.service.impl;

import com.xebec.blog.dto.CreateTagRequest;
import com.xebec.blog.dto.TagDto;
import com.xebec.blog.entity.Tag;
import com.xebec.blog.mapper.TagMapper;
import com.xebec.blog.repository.TagRepository;
import com.xebec.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto createTag(CreateTagRequest createTagRequest) {

        String tagName = createTagRequest.getName();
        if(tagRepository.existsByName(tagName)) {
            throw new IllegalArgumentException("Tag already exists with name: " + tagName);
        }

        Tag tag = tagMapper.toEntity(createTagRequest);
        Tag createdTag = tagRepository.save(tag);
        return tagMapper.toDto(createdTag);
    }

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .toList();
    }
}
