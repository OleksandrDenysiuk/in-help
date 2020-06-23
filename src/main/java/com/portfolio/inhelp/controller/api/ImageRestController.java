package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageRestController {
    private final ImageService imageService;

    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/accidents/{accidentId}/images")
    public @ResponseBody
    ImageDto create(@RequestParam MultipartFile multipartFile,
                    @PathVariable Long accidentId) throws IOException {
        return imageService.create(multipartFile, accidentId, 1L);
    }

    @PostMapping("/accidents/{accidentId}/news/{newsId}/images")
    public @ResponseBody
    ImageDto create(@RequestParam MultipartFile multipartFile,
                    @PathVariable Long accidentId,
                    @PathVariable Long newsId) throws IOException {
        return imageService.create(multipartFile, accidentId, newsId, 1L);
    }

    @DeleteMapping("/accidents/{accidentId}/images/{imageId}")
    public @ResponseBody
    void delete(@PathVariable Long imageId,
                @PathVariable Long accidentId) {
        imageService.delete(imageId, accidentId, 1L);
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}/images/{imageId}")
    public @ResponseBody
    void delete(@PathVariable Long imageId,
                @PathVariable Long accidentId,
                @PathVariable Long newsId) {
        imageService.delete(imageId, accidentId, newsId, 1L);
    }

}
