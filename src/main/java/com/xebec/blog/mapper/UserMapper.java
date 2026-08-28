package com.xebec.blog.mapper;

import com.xebec.blog.dto.AuthorDto;
import com.xebec.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    AuthorDto toAuthorDto(User user);
}
