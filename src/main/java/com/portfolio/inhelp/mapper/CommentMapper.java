package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "newsId", source = "news.id")
    CommentDto toDto(Comment comment);
}
