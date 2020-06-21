package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsRestController {
    private final NewsService newsService;

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public @ResponseBody
    List<NewsDto> getAll() {
        return newsService.getAll();
    }

    @GetMapping("/accidents/{accidentId}/news")
    public @ResponseBody
    List<NewsDto> getAllByAccident(@PathVariable Long accidentId) {
        return newsService.getAllByAccidentId(accidentId);
    }

    @GetMapping("/news/{newsId}")
    public @ResponseBody
    NewsDto getOne(@PathVariable Long newsId) {
        return newsService.getOne(newsId);
    }

    @PostMapping("/accidents/{accidentId}/news")
    public @ResponseBody
    NewsDto create(@RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId) {
        return newsService.create(newsCommand, accidentId, 1L);
    }

    @PutMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    NewsDto update(@RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId,
                   @PathVariable Long newsId) {
        newsCommand.setId(newsId);
        return newsService.update(newsCommand, accidentId, 1L);
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    void delete(@PathVariable Long accidentId,
                @PathVariable Long newsId) {
        newsService.delete(accidentId, newsId, 1L);
    }
}
