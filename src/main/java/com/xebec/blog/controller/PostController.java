package com.xebec.blog.controller;

import com.xebec.blog.dto.PostDto;
import com.xebec.blog.security.BlogUserDetails;
import com.xebec.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
       List<PostDto> posts = postService.getAllPosts(categoryId, tagId);
       return ResponseEntity.ok(posts);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(
            @AuthenticationPrincipal BlogUserDetails blogUserDetails) {
        UUID userId = blogUserDetails.getUser().getId();
        List<PostDto> posts = postService.getUserDraftPosts(userId);
        return ResponseEntity.ok(posts);
    }

}
