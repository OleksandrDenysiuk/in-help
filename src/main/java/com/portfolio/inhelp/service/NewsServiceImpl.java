package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.exception.AccidentNotFoundException;
import com.portfolio.inhelp.exception.NewsNotFoundException;
import com.portfolio.inhelp.exception.UserNotFoundException;
import com.portfolio.inhelp.mapper.NewsMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        return newsRepository.findById(newsId)
                .map(NewsMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
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
            throw new AccidentNotFoundException(accidentId);
        }
    }

    @Override
    public NewsDto create(NewsCommand newsCommand, Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                News news = News.builder()
                        .title(newsCommand.getTitle())
                        .content(newsCommand.getContent())
                        .images(new HashSet<>())
                        .comments(new HashSet<>())
                        .build();
                accident.addNews(news);
                return NewsMapper.INSTANCE.toDto(newsRepository.save(news));
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public NewsDto update(NewsCommand newsCommand, Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(newsCommand.getId()))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    news.setTitle(newsCommand.getTitle());
                    news.setContent(newsCommand.getContent());
                    return NewsMapper.INSTANCE.toDto(newsRepository.save(news));
                } else {
                    throw new NewsNotFoundException(newsCommand.getId());
                }
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(userId);
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
                    throw new NewsNotFoundException(newsId);
                }
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}
