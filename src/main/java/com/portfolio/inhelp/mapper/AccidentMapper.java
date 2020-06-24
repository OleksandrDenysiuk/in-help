package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Comment;
import com.portfolio.inhelp.model.Image;
import com.portfolio.inhelp.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccidentMapper {

    AccidentMapper INSTANCE = Mappers.getMapper(AccidentMapper.class);

    @Mapping(target = "userId", source = "user.id")
    AccidentDto toDto(Accident accident);

    @Mapping(target = "accidentId", source = "accident.id")
    NewsDto toDto(News news);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "authorId", source = "author.id")
    CommentDto toDto(Comment comment);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "newsId", source = "news.id")
    ImageDto toDto(Image image);
}
