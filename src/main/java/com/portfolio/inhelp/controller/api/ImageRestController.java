package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.service.ImageService;
import com.portfolio.inhelp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageRestController {
    private final ImageService imageService;
    private final UserService userService;

    public ImageRestController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping("/accidents/{accidentId}/images")
    public @ResponseBody
    ImageDto create(@AuthenticationPrincipal UserDetails userDetails,
                    @RequestParam MultipartFile multipartFile,
                    @PathVariable Long accidentId) throws IOException {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            return imageService.create(multipartFile, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }

    @PostMapping("/accidents/{accidentId}/news/{newsId}/images")
    public @ResponseBody
    ImageDto create(@AuthenticationPrincipal UserDetails userDetails,
                    @RequestParam MultipartFile multipartFile,
                    @PathVariable Long accidentId,
                    @PathVariable Long newsId) throws IOException {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            return imageService.create(multipartFile, accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }

    @DeleteMapping("/accidents/{accidentId}/images/{imageId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal UserDetails userDetails,
                @PathVariable Long imageId,
                @PathVariable Long accidentId) {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            imageService.delete(imageId, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}/images/{imageId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal UserDetails userDetails,
                @PathVariable Long imageId,
                @PathVariable Long accidentId,
                @PathVariable Long newsId) {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            imageService.delete(imageId, accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }

}
