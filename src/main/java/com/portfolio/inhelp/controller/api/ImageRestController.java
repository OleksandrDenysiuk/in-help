package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.ImageCommand;
import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.service.ImageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ImageRestController {
    private final ImageService imageService;

    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/accidents/{accidentId}/images")
    public @ResponseBody
    ImageDto create(@RequestBody ImageCommand imageCommand,
                    @PathVariable Long accidentId) {
        return imageService.create(imageCommand, accidentId, 1L);
    }

    @PostMapping("/accidents/{accidentId}/news/{newsId}/images")
    public @ResponseBody
    ImageDto create(@RequestBody ImageCommand imageCommand,
                    @PathVariable Long accidentId,
                    @PathVariable Long newsId) {
        return imageService.create(imageCommand, accidentId, newsId, 1L);
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
