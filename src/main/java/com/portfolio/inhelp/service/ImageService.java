package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.ImageDto;

import java.util.Set;

public interface ImageService {
    ImageDto getOne(Long imageId);
    Set<ImageDto> getAll();
    ImageDto create(ImageDto accidentCommand);
    ImageDto update(ImageDto accidentCommand);
    void delete(Long imageId);
}
