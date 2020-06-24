package com.portfolio.inhelp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccidentDto {

    private Long id;

    private String title;

    private String content;

    private Set<ImageDto> images;

    private Long userId;

    private Set<UserDto> helpers;

    private Set<CommentDto> comments;

    private Set<NewsDto> news;
}
