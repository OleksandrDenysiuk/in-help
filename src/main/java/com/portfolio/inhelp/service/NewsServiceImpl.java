package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.mapper.NewsMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final AccidentRepository accidentRepository;
    private final UserRepository userRepository;

    public NewsServiceImpl(NewsRepository newsRepository, AccidentRepository accidentRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.accidentRepository = accidentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NewsDto getOne(Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isPresent()) {
            return NewsMapper.INSTANCE.toDto(optionalNews.get());
        } else {
            throw new RuntimeException("News not found");
        }
    }

    @Override
    public List<NewsDto> getAll() {
        return newsRepository.findAll().stream()
                .map(NewsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsDto> getAllByAccidentId(Long accidentId) {
        Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
        if (optionalAccident.isPresent()) {
            Accident accident = optionalAccident.get();
            return accident.getNews().stream()
                    .map(NewsMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Accident not found");
        }
    }

    @Override
    public NewsDto create(NewsDto accidentCommand, Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                News news = News.builder().title(accidentCommand.getTitle())
                        .content(accidentCommand.getContent()).build();
                accident.addNews(news);
                return NewsMapper.INSTANCE.toDto(newsRepository.save(news));
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public NewsDto update(NewsDto accidentCommand, Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(accidentCommand.getId()))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    news.setTitle(accidentCommand.getTitle());
                    news.setContent(accidentCommand.getContent());
                    return NewsMapper.INSTANCE.toDto(newsRepository.save(news));
                } else {
                    throw new RuntimeException("News not found");
                }
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long newsId, Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(newsId))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    accident.removeNews(news);
                    newsRepository.delete(news);
                } else {
                    throw new RuntimeException("News not found");
                }
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
