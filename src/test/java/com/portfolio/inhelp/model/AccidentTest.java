package com.portfolio.inhelp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AccidentTest {
    Accident accident;

    @BeforeEach
    void setUp() {
        accident = Accident.builder().id(1L).images(new HashSet<>()).helpers(new HashSet<>()).build();
    }

    @Test
    void addImage() {
        Image imageToAdd = Image.builder().id(1L).build();
        accident.addImage(imageToAdd);

        assertEquals(1, accident.getImages().size());
        assertNotNull(accident.getImages().stream().filter(image -> image.getId().equals(1L)).findFirst().get());
    }

    @Test
    void removeImage() {
        Image imageToRemove = Image.builder().id(1L).build();
        accident.addImage(imageToRemove);

        accident.removeImage(Image.builder().id(1L).build());

        assertNotEquals(1, accident.getImages().size());
        assertThrows(NoSuchElementException.class, () -> {
           accident.getImages().stream().filter(image -> image.getId().equals(1L)).findFirst().get();
        });
    }

    @Test
    void addHelper() {
        User helperToAdd = User.builder().id(1L).accidents(new HashSet<>()).build();
        accident.addHelper(helperToAdd);


        assertEquals(1, accident.getHelpers().size());
        assertNotNull(accident.getHelpers().stream().filter(helper -> helper.getId().equals(1L)).findFirst().get());
    }

    @Test
    void removeHelper() {
        User helperToRemove = User.builder().id(1L).accidents(new HashSet<>()).build();

        accident.addHelper(helperToRemove);

        accident.removeHelper(User.builder().id(1L).accidents(new HashSet<>()).build());

        assertNotEquals(1, accident.getHelpers().size());
        assertThrows(NoSuchElementException.class, () -> {
            accident.getHelpers().stream().filter(helper -> helper.getId().equals(1L)).findFirst().get();
        });
    }
}