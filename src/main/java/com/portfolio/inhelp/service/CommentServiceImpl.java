package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.mapper.CommentMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Comment;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.CommentRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AccidentRepository accidentRepository;
    private final NewsRepository newsRepository;

    public CommentServiceImpl(UserRepository userRepository, CommentRepository commentRepository, AccidentRepository accidentRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.accidentRepository = accidentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<CommentDto> getAllByAccidentId(Long accidentId) {
        Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
        if (optionalAccident.isPresent()) {
            Accident accident = optionalAccident.get();
            return accident.getComments().stream()
                    .map(CommentMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Accident not found");
        }
    }

    @Override
    public List<CommentDto> getAllByNewsId(Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            return news.getComments().stream()
                    .map(CommentMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("News not found");
        }
    }

    @Override
    public CommentDto create(CommentCommand commentCommand, Long accidentId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Comment comment = Comment.builder()
                        .content(commentCommand.getContent())
                        .build();
                accident.addComment(comment);
                user.addComment(comment);
                return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public CommentDto create(CommentCommand commentCommand, Long accidentId, Long newsId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(newsId))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    Comment comment = Comment.builder()
                            .content(commentCommand.getContent())
                            .build();
                    News news = optionalNews.get();
                    news.addComment(comment);
                    user.addComment(comment);
                    return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
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
    public CommentDto update(CommentCommand commentCommand, Long accidentId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<Comment> optionalComment = accident.getComments().stream()
                        .filter(comment -> comment.getId().equals(commentCommand.getId()))
                        .findFirst();
                if (optionalComment.isPresent()) {
                    Comment comment = optionalComment.get();
                    if (comment.isAuthor(user)) {
                        comment.setContent(commentCommand.getContent());
                        return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
                    } else {
                        throw new RuntimeException("User not author");
                    }
                } else {
                    throw new RuntimeException("Comment not found");
                }
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public CommentDto update(CommentCommand commentCommand, Long accidentId, Long newsId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(newsId))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    Optional<Comment> optionalComment = news.getComments().stream()
                            .filter(comment -> comment.getId().equals(commentCommand.getId()))
                            .findFirst();
                    if (optionalComment.isPresent()) {
                        Comment comment = optionalComment.get();
                        if (comment.isAuthor(user)) {
                            comment.setContent(commentCommand.getContent());
                            return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
                        } else {
                            throw new RuntimeException("User not author");
                        }
                    } else {
                        throw new RuntimeException("Comment not found");
                    }
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
    public void delete(Long commentId, Long accidentId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<Comment> optionalComment = accident.getComments().stream()
                        .filter(comment -> comment.getId().equals(commentId))
                        .findFirst();
                if (optionalComment.isPresent()) {
                    Comment comment = optionalComment.get();
                    if(comment.isAuthor(user)){
                        user.removeComment(comment);
                        accident.removeComment(comment);
                        commentRepository.delete(optionalComment.get());
                    }else {
                        throw new RuntimeException("User not author");
                    }
                } else {
                    throw new RuntimeException("Comment not found");
                }
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long commentId, Long accidentId, Long newsId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<News> optionalNews = accident.getNews().stream()
                        .filter(news -> news.getId().equals(newsId))
                        .findFirst();
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    Optional<Comment> optionalComment = news.getComments().stream()
                            .filter(comment -> comment.getId().equals(commentId))
                            .findFirst();
                    if (optionalComment.isPresent()) {
                        Comment comment = optionalComment.get();
                        if(comment.isAuthor(user)){
                            user.removeComment(comment);
                            news.removeComment(comment);
                            commentRepository.delete(optionalComment.get());
                        }else {
                            throw new RuntimeException("User not author");
                        }
                    } else {
                        throw new RuntimeException("Comment not found");
                    }
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
