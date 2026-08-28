package com.xebec.blog.controller;

import com.xebec.blog.dto.CreateTagsRequest;
import com.xebec.blog.dto.TagDto;
import com.xebec.blog.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@Valid @RequestBody CreateTagsRequest createTagsRequest) {
       List<TagDto> createdTags = tagService.createTags(createTagsRequest);
       return new ResponseEntity<>(createdTags, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
       List<TagDto> tags = tagService.getAllTags();
       return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
