package com.portfolio.inhelp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).accidents(new HashSet<>()).comments(new HashSet<>()).build();
    }

    @Test
    void addAccident() {
        Accident accidentToAdd = Accident.builder().id(1L).build();
        user.addAccident(accidentToAdd);

        assertEquals(1, user.getAccidents().size());
        assertNotNull(user.getAccidents().stream().filter(accident -> accident.getId().equals(1L)).findFirst().get());
        assertTrue(user.getAccidents().contains(accidentToAdd));
    }

    @Test
    void removeAccident() {
        Accident accidentToRemove = Accident.builder().id(1L).build();

        user.addAccident(accidentToRemove);

        user.removeAccident(accidentToRemove);

        assertEquals(0, user.getAccidents().size());
        assertThrows(NoSuchElementException.class, () -> {
            user.getAccidents().stream().filter(accident -> accident.getId().equals(1L)).findFirst().get();
        });
    }

    @Test
    void addComment() {
        Comment commentToAdd = Comment.builder().id(1L).build();
        user.addComment(commentToAdd);

        assertEquals(1, user.getComments().size());
        assertNotNull(user.getComments().stream().filter(comment -> comment.getId().equals(1L)).findFirst().get());
    }

    @Test
    void removeComment() {
        Comment commentToRemove = Comment.builder().id(1L).build();

        user.addComment(commentToRemove);

        user.removeComment(commentToRemove);

        assertNotEquals(1, user.getComments().size());
        assertThrows(NoSuchElementException.class, () -> {
            user.getComments().stream().filter(comment -> comment.getId().equals(1L)).findFirst().get();
        });
    }
}