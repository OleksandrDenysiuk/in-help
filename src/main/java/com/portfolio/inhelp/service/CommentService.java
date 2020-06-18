package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.CommentDto;

import java.util.Set;

public interface CommentService {
    CommentDto getOne(Long commentId);
    Set<CommentDto> getAll();
    CommentDto create(CommentDto accidentCommand);
    CommentDto update(CommentDto accidentCommand);
    void delete(Long commentId);
}
