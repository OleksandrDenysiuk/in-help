package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
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
        return commentService.create(commentCommand, accidentId, accountDetails.getUserId());
    }

    @PostMapping("/accidents/{accidentId}/news/{newsId}/comments")
    public @ResponseBody
    CommentDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long newsId) {
        return commentService.create(commentCommand, accidentId, newsId, accountDetails.getUserId());
    }

    @PutMapping("/accidents/{accidentId}/comments/{commentId}")
    public @ResponseBody
    CommentDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long commentId) {
        commentCommand.setId(commentId);
        return commentService.update(commentCommand, accidentId, accountDetails.getUserId());
    }

    @PutMapping("/accidents/{accidentId}/news/{newsId}/comments/{commentId}")
    public @ResponseBody
    CommentDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                      @RequestBody CommentCommand commentCommand,
                      @PathVariable Long accidentId,
                      @PathVariable Long newsId,
                      @PathVariable Long commentId) {
        commentCommand.setId(commentId);
        return commentService.update(commentCommand, accidentId, newsId, accountDetails.getUserId());
    }

    @DeleteMapping("/accidents/{accidentId}/comments/{commentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long commentId) {
        commentService.delete(commentId, accidentId, accountDetails.getUserId());
    }

    @DeleteMapping("/accidents/{accidentId}/news/{newsId}/comments/{commentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId,
                @PathVariable Long newsId,
                @PathVariable Long commentId) {
        commentService.delete(commentId, accidentId, newsId, accountDetails.getUserId());
    }
}
