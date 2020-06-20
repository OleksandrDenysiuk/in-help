package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.ImageCommand;
import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.mapper.ImageMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Image;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.ImageRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImageServiceImpl implements ImageService {
    private final AccidentRepository accidentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(AccidentRepository accidentRepository, NewsRepository newsRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.accidentRepository = accidentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ImageDto> getAllByAccidentId(Long accidentId) {
        Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
        if (optionalAccident.isPresent()) {
            Accident accident = optionalAccident.get();
            return accident.getImages().stream()
                    .map(ImageMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Accident not found");
        }
    }

    @Override
    public List<ImageDto> getAllByNewsId(Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            return news.getImages().stream()
                    .map(ImageMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("News not found");
        }
    }

    @Override
    public ImageDto create(ImageCommand imageCommand, Long accidentId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Image image = Image.builder()
                        .url(imageCommand.getImage().getName())
                        .build();
                accident.addImage(image);
                return ImageMapper.INSTANCE.toDto(imageRepository.save(image));
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public ImageDto create(ImageCommand imageCommand, Long accidentId, Long newsId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
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
                    Image image = Image.builder()
                            .url(imageCommand.getImage().getName())
                            .build();
                    news.addImage(image);
                    return ImageMapper.INSTANCE.toDto(imageRepository.save(image));
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
    public void delete(Long imageId, Long accidentId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Optional<Image> optionalImage = accident.getImages().stream()
                        .filter(image -> image.getId().equals(imageId))
                        .findFirst();
                if (optionalImage.isPresent()) {
                    Image image = optionalImage.get();
                    accident.removeImage(image);
                    imageRepository.delete(image);
                } else {
                    throw new RuntimeException("Image not found");
                }
            } else {
                throw new RuntimeException("Accident not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long imageId, Long accidentId, Long newsId, Long authorId) {
        Optional<User> optionalUser = userRepository.findById(authorId);
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
                    Optional<Image> optionalImage = news.getImages().stream()
                            .filter(image -> image.getId().equals(imageId))
                            .findFirst();
                    if (optionalImage.isPresent()) {
                        Image image = optionalImage.get();
                        news.removeImage(image);
                        imageRepository.delete(image);
                    } else {
                        throw new RuntimeException("Image not found");
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
