package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "newsId", source = "news.id")
    ImageDto toDto(Image image);
}
