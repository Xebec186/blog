package com.xebec.blog.repository;

import com.xebec.blog.entity.Post;
import com.xebec.blog.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryIdAndTags_Id(PostStatus status, UUID categoryId, UUID tagId);
    List<Post> findAllByStatusAndCategoryId(PostStatus status, UUID categoryId);
    List<Post> findAllByStatusAndTags_Id(PostStatus status, UUID tagId);
    List<Post> findAllByStatus(PostStatus status);
}