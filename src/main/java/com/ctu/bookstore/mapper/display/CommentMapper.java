package com.ctu.bookstore.mapper.display;

import com.ctu.bookstore.dto.respone.display.CommentResponse;
import com.ctu.bookstore.entity.display.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user.username", target = "username")
    CommentResponse toCommentResponse(Comment comment);
}
