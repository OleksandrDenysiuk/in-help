package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    NewsRepository newsRepository;
    @Mock
    AccidentRepository accidentRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    NewsServiceImpl newsService;

    @Test
    void getOne() {
        News news = News.builder().id(1L).build();

        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        NewsDto newsDto = newsService.getOne(1L);

        assertNotNull(newsDto);
        assertEquals(1,newsDto.getId());
        verify(newsRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAll() {
        News news1 = News.builder().id(1L).build();
        News news2 = News.builder().id(2L).build();
        List<News> newsList = new ArrayList<>();
        newsList.add(news1);
        newsList.add(news2);
        when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsDto> newsDtoList = newsService.getAll();

        assertEquals(2,newsDtoList.size());
        verify(newsRepository,times(1)).findAll();
    }

    @Test
    void getAllByAccidentId() {
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news1 = News.builder().id(1L).build();
        News news2 = News.builder().id(2L).build();
        accident.addNews(news1);
        accident.addNews(news2);

        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        List<NewsDto> newsDtoList = newsService.getAllByAccidentId(1L);

        assertEquals(2, newsDtoList.size());
        verify(accidentRepository,times(1)).findById(anyLong());
    }

    @Test
    void create() {
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        user.addAccident(accident);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(newsRepository.save(any())).thenReturn(new News());

        NewsDto newsDto = newsService.create(new NewsCommand(), 1L, 1L);

        assertNotNull(newsDto);
        verify(userRepository,times(1)).findById(anyLong());
        assertEquals(1,accident.getNews().size());
    }

    @Test
    void update() {
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news = News.builder().id(1L).title("title").content("content").build();
        accident.addNews(news);
        user.addAccident(accident);
        NewsCommand newsCommand = new NewsCommand();
        newsCommand.setId(1L);
        newsCommand.setTitle("title2");
        newsCommand.setContent("content2");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(newsRepository.save(any())).thenReturn(new News());

        NewsDto newsDto = newsService.update(newsCommand, 1L, 1L);

        assertNotNull(newsDto);
        verify(userRepository,times(1)).findById(anyLong());
        verify(newsRepository,times(1)).save(any());
        assertEquals("title2",news.getTitle());
        assertEquals("content2",news.getContent());
    }

    @Test
    void delete() {
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news = News.builder().id(1L).build();
        accident.addNews(news);
        user.addAccident(accident);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        newsService.delete(1L, 1L, 1L);

        verify(userRepository,times(1)).findById(anyLong());
        verify(newsRepository,times(1)).delete(any());
        assertEquals(0,accident.getNews().size());
    }
}