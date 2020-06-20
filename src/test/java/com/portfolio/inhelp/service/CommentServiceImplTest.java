package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Comment;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.CommentRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    AccidentRepository accidentRepository;
    @Mock
    NewsRepository newsRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void getAllByAccidentId() {
        Accident accident = Accident.builder().id(1L).comments(new HashSet<>()).build();
        accident.addComment(Comment.builder().id(1L).build());
        accident.addComment(Comment.builder().id(2L).build());
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        List<CommentDto> commentDtoList = commentService.getAllByAccidentId(1L);

        assertEquals(2, commentDtoList.size());
    }

    @Test
    void getAllByNewsId() {
        News news = News.builder().id(1L).comments(new HashSet<>()).build();
        news.addComment(Comment.builder().id(1L).build());
        news.addComment(Comment.builder().id(2L).build());
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        List<CommentDto> commentDtoList = commentService.getAllByNewsId(1L);

        assertEquals(2, commentDtoList.size());
    }

    @Test
    void createInAccident() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).comments(new HashSet<>()).build();
        Comment comment = Comment.builder().id(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto commentDto = commentService.create(new CommentCommand(), 1L , 1L);

        assertNotNull(commentDto);
        assertEquals(1,user.getComments().size());
        assertEquals(1,accident.getComments().size());
    }

    @Test
    void createInNews() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news = News.builder().id(1L).comments(new HashSet<>()).build();
        accident.addNews(news);
        Comment comment = Comment.builder().id(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto commentDto = commentService.create(new CommentCommand(), 1L , 1L, 1L);

        assertNotNull(commentDto);
        assertEquals(1,user.getComments().size());
        assertEquals(1,news.getComments().size());
    }

    @Test
    void updateInAccident() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).comments(new HashSet<>()).build();
        Comment comment = Comment.builder().id(1L).content("test").build();
        comment.setAuthor(user);
        accident.addComment(comment);
        CommentCommand command = new CommentCommand();
        command.setId(1L);
        command.setContent("test2");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto commentDto = commentService.update(command, 1L , 1L);

        assertNotNull(commentDto);
        assertNotEquals("test", commentDto.getContent());
        assertEquals("test2", commentDto.getContent());
        verify(userRepository, times(1)).findById(anyLong());
        verify(accidentRepository, times(1)).findById(anyLong());
        verify(commentRepository,times(1)).save(any());
    }

    @Test
    void updateInNews() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        Comment comment = Comment.builder().id(1L).content("test").build();
        comment.setAuthor(user);
        News news = News.builder().id(1L).comments(new HashSet<>()).build();
        accident.addNews(news);
        news.addComment(comment);
        CommentCommand command = new CommentCommand();
        command.setId(1L);
        command.setContent("test2");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto commentDto = commentService.update(command, 1L , 1L, 1L);

        assertNotNull(commentDto);
        assertNotEquals("test", commentDto.getContent());
        assertEquals("test2", commentDto.getContent());
        verify(userRepository, times(1)).findById(anyLong());
        verify(accidentRepository, times(1)).findById(anyLong());
        verify(commentRepository,times(1)).save(any());
    }

    @Test
    void deleteInAccident() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).comments(new HashSet<>()).build();
        Comment comment = Comment.builder().id(1L).build();
        comment.setAuthor(user);
        accident.addComment(comment);
        CommentCommand command = new CommentCommand();
        command.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        commentService.delete(1L, 1L , 1L);

        assertEquals(0,user.getComments().size());
        assertEquals(0,accident.getComments().size());
        verify(userRepository, times(1)).findById(anyLong());
        verify(accidentRepository, times(1)).findById(anyLong());
        verify(commentRepository,times(1)).delete(any());
    }

    @Test
    void deleteInNews() {
        User user = User.builder().id(1L).comments(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        Comment comment = Comment.builder().id(1L).build();
        comment.setAuthor(user);
        News news = News.builder().id(1L).comments(new HashSet<>()).build();
        accident.addNews(news);
        news.addComment(comment);
        CommentCommand command = new CommentCommand();
        command.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        commentService.delete(1L, 1L , 1L, 1L);

        assertEquals(0,user.getComments().size());
        assertEquals(0,news.getComments().size());
        verify(userRepository, times(1)).findById(anyLong());
        verify(accidentRepository, times(1)).findById(anyLong());
        verify(commentRepository,times(1)).delete(any());
    }
}