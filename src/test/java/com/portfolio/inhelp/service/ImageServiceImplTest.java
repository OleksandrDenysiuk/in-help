package com.portfolio.inhelp.service;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Image;
import com.portfolio.inhelp.model.News;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.ImageRepository;
import com.portfolio.inhelp.repository.NewsRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    AccidentRepository accidentRepository;
    @Mock
    NewsRepository newsRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ImageRepository imageRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void getAllByAccidentId() {
        Accident accident = Accident.builder().id(1L).images(new HashSet<>()).build();
        accident.addImage(Image.builder().id(1L).build());
        accident.addImage(Image.builder().id(2L).build());
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        List<ImageDto> imageDtoList = imageService.getAllByAccidentId(1L);

        assertEquals(2, imageDtoList.size());
        verify(accidentRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllByNewsId() {
        News news = News.builder().id(1L).images(new HashSet<>()).build();
        news.addImage(Image.builder().id(1L).build());
        news.addImage(Image.builder().id(2L).build());
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        List<ImageDto> imageDtoList = imageService.getAllByNewsId(1L);

        assertEquals(2, imageDtoList.size());
        verify(newsRepository, times(1)).findById(anyLong());
    }

    @Test
    void createInAccident() throws IOException {
        Accident accident = Accident.builder().id(1L).images(new HashSet<>()).build();
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        user.addAccident(accident);
        Image image = Image.builder().id(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(imageRepository.save(any())).thenReturn(image);

        ImageDto imageDto = imageService.create(new MultipartFile() {
            @Override
            public String getName() {
                return "image";
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        }, 1L, 1L);

        assertNotNull(imageDto);
        assertEquals(1, accident.getImages().size());
    }

    @Test
    void createInNews() throws IOException {
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news = News.builder().id(1L).images(new HashSet<>()).build();
        accident.addNews(news);
        user.addAccident(accident);
        Image image = Image.builder().id(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(imageRepository.save(any())).thenReturn(image);

        ImageDto imageDto = imageService.create(new MultipartFile() {
            @Override
            public String getName() {
                return "image";
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        }, 1L, 1L, 1L);

        assertNotNull(imageDto);
        assertEquals(1, news.getImages().size());
    }

    @Test
    void deleteInAccident() {
        User user = User.builder().accidents(new HashSet<>()).id(1L).build();
        Accident accident = Accident.builder().id(1L).images(new HashSet<>()).build();
        Image image = Image.builder().id(1L).build();
        accident.addImage(image);
        user.addAccident(accident);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        imageService.delete(1L, 1L, 1L);

        assertEquals(0, accident.getImages().size());
        verify(userRepository, times(1)).findById(anyLong());
        verify(imageRepository, times(1)).delete(any());
    }

    @Test
    void deleteInNews() {
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        Accident accident = Accident.builder().id(1L).news(new HashSet<>()).build();
        News news = News.builder().id(1L).images(new HashSet<>()).build();
        Image image = Image.builder().id(1L).build();
        news.addImage(image);
        accident.addNews(news);
        user.addAccident(accident);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        imageService.delete(1L, 1L, 1L, 1L);

        assertEquals(0, news.getImages().size());
        verify(userRepository, times(1)).findById(anyLong());
        verify(imageRepository, times(1)).delete(any());
    }
}