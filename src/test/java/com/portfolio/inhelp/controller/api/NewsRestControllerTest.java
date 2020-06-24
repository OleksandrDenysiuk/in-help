package com.portfolio.inhelp.controller.api;

import com.google.gson.Gson;
import com.portfolio.inhelp.command.NewsCommand;
import com.portfolio.inhelp.dto.NewsDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.model.Role;
import com.portfolio.inhelp.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NewsRestControllerTest {

    @Mock
    NewsService newsService;

    @InjectMocks
    NewsRestController newsRestController;

    MockMvc mockMvc;

    HandlerMethodArgumentResolver putPrincipal;

    @BeforeEach
    void setUp() {
        putPrincipal = new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType().isAssignableFrom(AccountDetails.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                return new AccountDetails(1L, "u","1", Collections.singleton(new Role(1L,"USER")));
            }
        };
        mockMvc = MockMvcBuilders.standaloneSetup(newsRestController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void getAll() throws Exception {
        NewsDto news1 = new NewsDto();
        news1.setId(1L);
        NewsDto news2 = new NewsDto();
        news2.setId(2L);
        List<NewsDto> list = new ArrayList<>();
        list.add(news1);
        list.add(news2);

        when(newsService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(newsService, times(1)).getAll();
    }

    @Test
    void getAllByAccident() throws Exception {
        NewsDto news1 = new NewsDto();
        news1.setId(1L);
        NewsDto news2 = new NewsDto();
        news2.setId(2L);
        List<NewsDto> list = new ArrayList<>();
        list.add(news1);
        list.add(news2);

        when(newsService.getAllByAccidentId(anyLong())).thenReturn(list);

        mockMvc.perform(get("/api/accidents/1/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(newsService, times(1)).getAllByAccidentId(anyLong());
    }

    @Test
    void getOne() throws Exception {
        NewsDto news1 = new NewsDto();
        news1.setId(1L);

        when(newsService.getOne(anyLong())).thenReturn(news1);

        mockMvc.perform(get("/api/news/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

        verify(newsService, times(1)).getOne(anyLong());
    }

    @Test
    void create() throws Exception {
        NewsDto news1 = new NewsDto();
        news1.setId(1L);

        when(newsService.create(any(), anyLong(), anyLong())).thenReturn(news1);

        mockMvc.perform(post("/api/accidents/1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new NewsCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

        verify(newsService, times(1)).create(any(), anyLong(), anyLong());
    }

    @Test
    void update() throws Exception {
        NewsDto news1 = new NewsDto();
        news1.setId(1L);

        when(newsService.update(any(), anyLong(), anyLong())).thenReturn(news1);

        mockMvc.perform(put("/api/accidents/1/news/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new NewsCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

        verify(newsService, times(1)).update(any(), anyLong(), anyLong());
    }

    @Test
    void deleteNews() throws Exception {
        mockMvc.perform(delete("/api/accidents/1/news/1"))
                .andExpect(status().isOk());

        verify(newsService, times(1)).delete(anyLong(), anyLong(), anyLong());
    }
}