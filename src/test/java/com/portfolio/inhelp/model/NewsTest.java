package com.portfolio.inhelp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class NewsTest {
    News news;

    @BeforeEach
    void setUp() {
        news = News.builder().id(1L).images(new HashSet<>()).comments(new HashSet<>()).build();
    }

    @Test
    void addImage() {
        Image imageToAdd = Image.builder().id(1L).build();
        news.addImage(imageToAdd);

        assertEquals(1, news.getImages().size());
        assertNotNull(news.getImages().stream().filter(image -> image.getId().equals(1L)).findFirst().get());
    }

    @Test
    void removeImage() {
        Image imageToRemove = Image.builder().id(1L).build();
        news.addImage(imageToRemove);

        news.removeImage(imageToRemove);

        assertNotEquals(1, news.getImages().size());
        assertThrows(NoSuchElementException.class, () -> {
            news.getImages().stream().filter(image -> image.getId().equals(1L)).findFirst().get();
        });
    }

    @Test
    void addComment() {
        Comment commentToAdd = Comment.builder().id(1L).build();
        news.addComment(commentToAdd);

        assertEquals(1, news.getComments().size());
        assertNotNull(news.getComments().stream().filter(comment -> comment.getId().equals(1L)).findFirst().get());
    }

    @Test
    void removeComment() {
        Comment commentToRemove = Comment.builder().id(1L).build();

        news.addComment(commentToRemove);

        news.removeComment(commentToRemove);

        assertNotEquals(1, news.getComments().size());
        assertThrows(NoSuchElementException.class, () -> {
            news.getComments().stream().filter(comment -> comment.getId().equals(1L)).findFirst().get();
        });
    }
}