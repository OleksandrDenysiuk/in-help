package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.service.CommentService;
import com.portfolio.inhelp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentRestController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/accidents/{accidentId}/comments")
    public @ResponseBody
    List<CommentDto> getAllByAccident(@PathVariable Long accidentId) {
        return commentService.getAllByAccidentId(accidentId);
    }

    @GetMapping("/news/{newsId}/comments")
    public @ResponseBody
    List<CommentDto> getAllByNews(@PathVariable Long newsId) {
        return commentService.getAllByNewsId(newsId);
    }

    @PostMapping("/accidents/{accidentId}/comments")
    public @ResponseBody
    CommentDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            return commentService.create(commentCommand, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @PostMapping("/accidents/{accidentId}/news/{newsId}/comments")
    public @ResponseBody
    CommentDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long newsId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            return commentService.create(commentCommand, accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @PutMapping("/accidents/{accidentId}/comments/{commentId}")
    public @ResponseBody
    CommentDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long commentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            commentCommand.setId(commentId);
            return commentService.update(commentCommand, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @PutMapping("/accidents/{accidentId}/news/{newsId}/comments/{commentId}")
    public @ResponseBody
    CommentDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long newsId,
                      @PathVariable Long commentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            commentCommand.setId(commentId);
            return commentService.update(commentCommand, accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @DeleteMapping("/accidents/{accidentId}/comments/{commentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long commentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            commentService.delete(commentId, accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}/comments/{commentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long newsId,
                @PathVariable Long commentId) {
        UserDto user = userService.getOneByUsername(accountDetails.getUsername());
        if (user != null) {
            commentService.delete(commentId, accidentId, newsId, user.getId());
        } else {
            throw new UsernameNotFoundException(accountDetails.getUsername());
        }

    }
}
