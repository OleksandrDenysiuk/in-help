package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
