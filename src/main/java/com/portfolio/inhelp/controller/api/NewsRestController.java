package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.service.NewsService;
import com.portfolio.inhelp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsRestController {
    private final NewsService newsService;
    private final UserService userService;

    public NewsRestController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
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
                   @RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            return newsService.create(newsCommand, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @PutMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    NewsDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                   @RequestBody NewsCommand newsCommand,
                   @PathVariable Long accidentId,
                   @PathVariable Long newsId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            newsCommand.setId(newsId);
            return newsService.update(newsCommand, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }

    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long newsId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            newsService.delete(accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }
}
