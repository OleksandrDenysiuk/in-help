package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
