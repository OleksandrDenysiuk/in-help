package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageDto> getAllByAccidentId(Long accidentId);
    List<ImageDto> getAllByNewsId(Long newsIs);
    ImageDto create(MultipartFile image, Long accidentId, Long authorId) throws IOException;
    ImageDto create(MultipartFile image, Long accidentId, Long newsId, Long authorId) throws IOException;
    void delete(Long imageId, Long accidentId, Long authorId);
    void delete(Long imageId, Long accidentId, Long newsId, Long authorId);
}
