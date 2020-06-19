package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllByAccidentId(Long accidentId);
    List<CommentDto> getAllByNewsId(Long newsId);

    CommentDto create(CommentCommand commentCommand, Long accidentId, Long authorId);
    CommentDto create(CommentCommand commentCommand, Long accidentId, Long newsId, Long authorId);

    CommentDto update(CommentCommand commentCommand, Long accidentId, Long authorId);
    CommentDto update(CommentCommand commentCommand, Long accidentId, Long newsId, Long authorId);

    void delete(Long commentId, Long accidentId, Long authorId);
    void delete(Long commentId, Long accidentId, Long newsId, Long authorId);
}
