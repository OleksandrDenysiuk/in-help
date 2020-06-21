package com.portfolio.inhelp.controller.api;

import com.google.gson.Gson;
import com.portfolio.inhelp.command.CommentCommand;
import com.portfolio.inhelp.dto.CommentDto;
import com.portfolio.inhelp.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentRestControllerTest {
    @Mock
    CommentService commentService;

    @InjectMocks
    CommentRestController commentRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentRestController).build();
    }

    @Test
    void getAllByAccident() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);
        CommentDto comment2 = new CommentDto();
        comment2.setId(2L);
        List<CommentDto> commentDtos = new ArrayList<>();
        commentDtos.add(comment1);
        commentDtos.add(comment2);

        when(commentService.getAllByAccidentId(anyLong())).thenReturn(commentDtos);

        mockMvc.perform(get("/api/accidents/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(commentService, times(1)).getAllByAccidentId(anyLong());
    }

    @Test
    void getAllByNews() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);
        CommentDto comment2 = new CommentDto();
        comment2.setId(2L);
        List<CommentDto> commentDtos = new ArrayList<>();
        commentDtos.add(comment1);
        commentDtos.add(comment2);

        when(commentService.getAllByNewsId(anyLong())).thenReturn(commentDtos);

        mockMvc.perform(get("/api/news/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(commentService, times(1)).getAllByNewsId(anyLong());
    }

    @Test
    void createInAccident() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);

        when(commentService.create(any(), anyLong(), anyLong())).thenReturn(comment1);

        mockMvc.perform(post("/api/accidents/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CommentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(commentService, times(1)).create(any(), anyLong(), anyLong());
    }

    @Test
    void createInNews() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);

        when(commentService.create(any(), anyLong(), anyLong(), anyLong())).thenReturn(comment1);

        mockMvc.perform(post("/api/accidents/1/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CommentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(commentService, times(1)).create(any(), anyLong(), anyLong(), anyLong());
    }

    @Test
    void updateInAccident() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);

        when(commentService.update(any(), anyLong(), anyLong())).thenReturn(comment1);

        mockMvc.perform(put("/api/accidents/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CommentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(commentService, times(1)).update(any(), anyLong(), anyLong());
    }

    @Test
    void updateInNews() throws Exception {
        CommentDto comment1 = new CommentDto();
        comment1.setId(1L);

        when(commentService.update(any(), anyLong(), anyLong(), anyLong())).thenReturn(comment1);

        mockMvc.perform(put("/api/accidents/1/news/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CommentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(commentService, times(1)).update(any(), anyLong(), anyLong(), anyLong());
    }

    @Test
    void deleteInAccident() throws Exception {
        mockMvc.perform(delete("/api/accidents/1/comments/1"))
                .andExpect(status().isOk());

        verify(commentService, times(1)).delete(anyLong(), anyLong(), anyLong());
    }

    @Test
    void deleteInNews() throws Exception {
        mockMvc.perform(delete("/api/accidents/1/news/1/comments/1"))
                .andExpect(status().isOk());

        verify(commentService, times(1)).delete(anyLong(), anyLong(), anyLong(), anyLong());
    }
}