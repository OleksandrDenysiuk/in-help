package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;

import java.util.List;

public interface NewsService {
    NewsDto getOne(Long newsId);
    List<NewsDto> getAll();
    List<NewsDto> getAllByAccidentId(Long accidentId);
    NewsDto create(NewsCommand newsCommand, Long accidentId, Long userId);
    NewsDto update(NewsCommand newsCommand, Long accidentId, Long userId);
    void delete(Long newsId, Long accidentId, Long userId);
}
