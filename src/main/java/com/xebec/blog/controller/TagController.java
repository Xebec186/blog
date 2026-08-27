package com.xebec.blog.controller;

import com.xebec.blog.dto.CreateTagRequest;
import com.xebec.blog.dto.TagDto;
import com.xebec.blog.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody CreateTagRequest createTagRequest) {
       TagDto createdTag = tagService.createTag(createTagRequest);
       return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
       List<TagDto> tags = tagService.getAllTags();
       return ResponseEntity.ok(tags);
    }
}
