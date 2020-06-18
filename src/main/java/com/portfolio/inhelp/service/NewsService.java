package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.NewsDto;

import java.util.Set;

public interface NewsService {
    NewsDto getOne(Long newsId);
    Set<NewsDto> getAll();
    NewsDto create(NewsDto accidentCommand);
    NewsDto update(NewsDto accidentCommand);
    void delete(Long newsId);
}
