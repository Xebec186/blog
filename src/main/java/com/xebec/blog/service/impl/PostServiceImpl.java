package com.xebec.blog.service.impl;

import com.xebec.blog.dto.CreatePostRequest;
import com.xebec.blog.dto.PostDto;
import com.xebec.blog.dto.UpdatePostRequest;
import com.xebec.blog.entity.Category;
import com.xebec.blog.entity.Post;
import com.xebec.blog.entity.Tag;
import com.xebec.blog.entity.User;
import com.xebec.blog.enums.PostStatus;
import com.xebec.blog.exception.ResourceNotFoundException;
import com.xebec.blog.mapper.PostMapper;
import com.xebec.blog.repository.CategoryRepository;
import com.xebec.blog.repository.PostRepository;
import com.xebec.blog.repository.TagRepository;
import com.xebec.blog.repository.UserRepository;
import com.xebec.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final int WORDS_PER_MINUTE = 200;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

    @Override
    public PostDto createPost(CreatePostRequest createPostRequest, UUID userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        UUID categoryId = createPostRequest.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id: " + categoryId));

        List<Tag> tagsList = tagRepository.findAllById(createPostRequest.getTagIds());
        Set<Tag> tagsSet = new HashSet<>(tagsList);

        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .status(createPostRequest.getStatus())
                .readingTime(calculateReadingTime(createPostRequest.getContent()))
                .author(author)
                .category(category)
                .tags(tagsSet)
                .build();

        Post createdPost = postRepository.save(post);

        return postMapper.toDto(createdPost);
    }

    @Override
    public PostDto getPost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with given id: " + id));
        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null) {
           return postRepository.findAllByStatusAndCategoryIdAndTags_Id(PostStatus.PUBLISHED, categoryId, tagId).stream()
                   .map(postMapper::toDto)
                   .toList();
        }

        if(categoryId != null) {
            return postRepository.findAllByStatusAndCategoryId(PostStatus.PUBLISHED, categoryId).stream()
                    .map(postMapper::toDto)
                    .toList();
        }

        if(tagId != null) {
            return postRepository.findAllByStatusAndTags_Id(PostStatus.PUBLISHED, tagId).stream()
                    .map(postMapper::toDto)
                    .toList();
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getUserDraftPosts(UUID userId) {
         return postRepository.findAllByStatusAndAuthorId(PostStatus.DRAFT, userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public PostDto updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with given id: " + id));

        UUID categoryId = updatePostRequest.getCategoryId();
        if(!post.getCategory().getId().equals(categoryId)) {
            Category newCategory = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id: " + categoryId));
            post.setCategory(newCategory);
        }

        Set<UUID> updatePostRequestTagIdsIds = updatePostRequest.getTagIds();
        Set<UUID> existingTagIds = post.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        if(!existingTagIds.equals(updatePostRequestTagIdsIds)) {
            List<Tag> newTagsList = tagRepository.findAllById(updatePostRequestTagIdsIds);
            post.setTags(new HashSet<>(newTagsList));
        }

        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));
        post.setStatus(updatePostRequest.getStatus());

        Post updatedPost = postRepository.save(post);

        return postMapper.toDto(updatedPost);
    }

    @Override
    public void deletePost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with given id: " + id));
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if(content == null || content.isEmpty()) return 0;
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}