package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDto toDto(News news);
}
