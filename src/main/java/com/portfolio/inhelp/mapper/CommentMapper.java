package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comment comment);
}
