package com.xebec.blog.controller;

import com.xebec.blog.dto.TagDto;
import com.xebec.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
       List<TagDto> tags = tagService.getAllTags();
       return ResponseEntity.ok(tags);
    }
}
