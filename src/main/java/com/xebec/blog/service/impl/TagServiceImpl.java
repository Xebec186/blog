package com.xebec.blog.service.impl;

import com.xebec.blog.dto.CreateTagsRequest;
import com.xebec.blog.dto.TagDto;
import com.xebec.blog.entity.Tag;
import com.xebec.blog.mapper.TagMapper;
import com.xebec.blog.repository.TagRepository;
import com.xebec.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    @Override
    public List<TagDto> createTags(CreateTagsRequest createTagsRequest) {

        Set<String> tagNames = createTagsRequest.getNames();
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> createdTags = new ArrayList<>();
        if(!newTags.isEmpty()) {
            createdTags = tagRepository.saveAll(newTags);
        }


        createdTags.addAll(existingTags);

        return createdTags.stream()
                .map(tagMapper::toDto)
                .toList();
    }

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAllWithPosts().stream()
                .map(tagMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if(tag.isPresent()) {
            if(!tag.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        }
    }
}
