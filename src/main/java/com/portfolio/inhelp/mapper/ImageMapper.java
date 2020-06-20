package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDto toDto(Image image);
}
