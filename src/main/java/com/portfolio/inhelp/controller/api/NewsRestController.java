package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.service.NewsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    NewsDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                   @Valid @RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId) {
        return newsService.create(newsCommand, accidentId, accountDetails.getUserId());
    }

    @PutMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    NewsDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                   @Valid @RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId,
                   @PathVariable Long newsId) {
        newsCommand.setId(newsId);
        return newsService.update(newsCommand, accidentId, accountDetails.getUserId());
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long newsId) {
        newsService.delete(accidentId, newsId, accountDetails.getUserId());
    }
}
