package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.exception.AccidentNotFoundException;
import com.portfolio.inhelp.exception.ImageNotFoundException;
import com.portfolio.inhelp.exception.NewsNotFoundException;
import com.portfolio.inhelp.exception.UserNotFoundException;
import com.portfolio.inhelp.mapper.ImageMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Image;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.ImageRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
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
            throw new AccidentNotFoundException(accidentId);
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
            throw new NewsNotFoundException(newsId);
        }
    }

    @Override
    @Transactional
    public ImageDto create(MultipartFile image, Long accidentId, Long authorId) throws IOException {
        Optional<User> optionalUser = userRepository.findById(authorId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                Image imageToSave = Image.builder().imageBytes(compressBytes(image.getBytes())).build();
                accident.addImage(imageToSave);
                return ImageMapper.INSTANCE.toDto(imageRepository.save(imageToSave));
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(authorId);
        }
    }

    @Override
    public ImageDto create(MultipartFile image, Long accidentId, Long newsId, Long authorId) throws IOException {
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
                    Image imageToSave = Image.builder().imageBytes(compressBytes(image.getBytes())).build();
                    news.addImage(imageToSave);
                    return ImageMapper.INSTANCE.toDto(imageRepository.save(imageToSave));
                } else {
                    throw new NewsNotFoundException(newsId);
                }
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(authorId);
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
                    throw new ImageNotFoundException(imageId);
                }
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(authorId);
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
                        throw new ImageNotFoundException(imageId);
                    }
                } else {
                    throw new NewsNotFoundException(newsId);
                }
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(authorId);
        }
    }

    // compress the image bytes before storing it in the database
    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
