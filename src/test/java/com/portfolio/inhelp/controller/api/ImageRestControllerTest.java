package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.dto.ImageDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.model.Role;
import com.portfolio.inhelp.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImageRestControllerTest {
    @Mock
    ImageService imageService;

    @InjectMocks
    ImageRestController imageRestController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(imageRestController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void createInAccident() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);
        byte[] image = new byte[]{1,1};
        MockMultipartFile multipartFile = new MockMultipartFile("image", image);

        when(imageService.create(any(), anyLong(), anyLong())).thenReturn(imageDto);

        mockMvc.perform(multipart("/api/accidents/1/images")
                .file("multipartFile", multipartFile.getBytes()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(imageService,times(1)).create(any(), anyLong(), anyLong());
    }

    @Test
    void createInNews() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);
        byte[] image = new byte[]{1,1};
        MockMultipartFile multipartFile = new MockMultipartFile("image", image);

        when(imageService.create(any(), anyLong(), anyLong(), anyLong())).thenReturn(imageDto);

        mockMvc.perform(multipart("/api/accidents/1/news/1/images")
                .file("multipartFile", multipartFile.getBytes()))
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