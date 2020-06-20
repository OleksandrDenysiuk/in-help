package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.ImageCommand;
import com.portfolio.inhelp.dto.ImageDto;

import java.util.List;

public interface ImageService {
    List<ImageDto> getAllByAccidentId(Long accidentId);
    List<ImageDto> getAllByNewsId(Long newsIs);
    ImageDto create(ImageCommand imageCommand, Long accidentId, Long authorId);
    ImageDto create(ImageCommand imageCommand, Long accidentId, Long newsId, Long authorId);
    void delete(Long imageId, Long accidentId, Long authorId);
    void delete(Long imageId, Long accidentId, Long newsId, Long authorId);
}
