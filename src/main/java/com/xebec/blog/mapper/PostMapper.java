package com.xebec.blog.mapper;

import com.xebec.blog.dto.PostDto;
import com.xebec.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = { CategoryMapper.class, TagMapper.class, UserMapper.class })
public interface PostMapper {
    PostDto toDto(Post post);
}
