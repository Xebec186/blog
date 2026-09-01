package com.xebec.blog.controller;

import com.xebec.blog.dto.CreatePostRequest;
import com.xebec.blog.dto.PostDto;
import com.xebec.blog.dto.UpdatePostRequest;
import com.xebec.blog.security.BlogUserDetails;
import com.xebec.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostRequest createPostRequest,
                                              @AuthenticationPrincipal BlogUserDetails blogUserDetails) {
        UUID userId = blogUserDetails.getUser().getId();
        PostDto createdPost = postService.createPost(createPostRequest, userId);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
       PostDto post = postService.getPost(id);
       return ResponseEntity.ok(post);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest updatePostRequest) {
       PostDto updatedPost = postService.updatePost(id, updatePostRequest);
       return ResponseEntity.ok(updatedPost);

    }

}
