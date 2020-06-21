package com.portfolio.inhelp.controller.api;

import com.google.gson.Gson;
import com.portfolio.inhelp.command.ImageCommand;
import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImageRestControllerTest {
    @Mock
    ImageService imageService;

    @InjectMocks
    ImageRestController imageRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageRestController).build();
    }

    @Test
    void createInAccident() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);

        when(imageService.create(any(), anyLong(), anyLong())).thenReturn(imageDto);

        mockMvc.perform(post("/api/accidents/1/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new ImageCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(imageService,times(1)).create(any(), anyLong(), anyLong());
    }

    @Test
    void createInNews() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);

        when(imageService.create(any(), anyLong(), anyLong(), anyLong())).thenReturn(imageDto);

        mockMvc.perform(post("/api/accidents/1/news/1/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new ImageCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(imageService,times(1)).create(any(), anyLong(), anyLong(), anyLong());
    }

    @Test
    void deleteInAccident() throws Exception {
        mockMvc.perform(delete("/api/accidents/1/images/1"))
                .andExpect(status().isOk());
        verify(imageService,times(1)).delete(anyLong(),anyLong(),anyLong());
    }

    @Test
    void deleteInNews() throws Exception {
        mockMvc.perform(delete("/api/accidents/1/news/1/images/1"))
                .andExpect(status().isOk());
        verify(imageService,times(1)).delete(anyLong(),anyLong(),anyLong(),anyLong());
    }
}