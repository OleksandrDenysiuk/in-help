package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.awt.*;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDto toDto(Image image);
}
