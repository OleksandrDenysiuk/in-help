package com.portfolio.inhelp.controller.api;

import com.google.gson.Gson;
import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.model.Role;
import com.portfolio.inhelp.service.AccidentService;
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
class AccidentRestControllerTest {

    @Mock
    AccidentService accidentService;

    @InjectMocks
    AccidentRestController accidentRestController;

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
                return new AccountDetails(1L,"u","1", Collections.singleton(new Role(1L,"USER")));
            }
        };
        mockMvc = MockMvcBuilders.standaloneSetup(accidentRestController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void getAll() throws Exception {
        AccidentDto accident1 = new AccidentDto();
        accident1.setId(1L);
        AccidentDto accident2 = new AccidentDto();
        accident2.setId(2L);
        List<AccidentDto> accidents = new ArrayList<>();
        accidents.add(accident1);
        accidents.add(accident2);

        when(accidentService.getAll()).thenReturn(accidents);

        mockMvc.perform(get("/api/accidents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(accidentService, times(1)).getAll();
    }

    @Test
    void getOne() throws Exception {
        AccidentDto accident1 = new AccidentDto();
        accident1.setId(1L);

        when(accidentService.getOne(anyLong())).thenReturn(accident1);

        mockMvc.perform(get("/api/accidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(accidentService, times(1)).getOne(anyLong());
    }

    @Test
    void getAllByUserId() throws Exception {
        AccidentDto accident1 = new AccidentDto();
        accident1.setId(1L);
        AccidentDto accident2 = new AccidentDto();
        accident2.setId(2L);
        List<AccidentDto> accidents = new ArrayList<>();
        accidents.add(accident1);
        accidents.add(accident2);

        when(accidentService.getAllByUserId(anyLong())).thenReturn(accidents);

        mockMvc.perform(get("/api/users/1/accidents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andReturn();

        verify(accidentService, times(1)).getAllByUserId(anyLong());
    }

    @Test
    void getOneByUserId() throws Exception {
        AccidentDto accident1 = new AccidentDto();
        accident1.setId(1L);

        when(accidentService.getOneByUserId(anyLong(), anyLong())).thenReturn(accident1);

        mockMvc.perform(get("/api/users/1/accidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();

        verify(accidentService, times(1)).getOneByUserId(anyLong(), anyLong());
    }

    @Test
    void create() throws Exception {
        AccidentDto accidentDto = new AccidentDto();
        accidentDto.setTitle("title");
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(accidentService.create(any(),anyLong())).thenReturn(accidentDto);

        mockMvc.perform(post("/api/accidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new AccidentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andReturn();

        verify(accidentService,times(1)).create(any(),anyLong());
    }

    @Test
    void update() throws Exception {
        AccidentDto accidentDto = new AccidentDto();
        accidentDto.setTitle("title");
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(accidentService.update(any(),anyLong())).thenReturn(accidentDto);

        mockMvc.perform(put("/api/accidents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new AccidentCommand())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andReturn();

        verify(accidentService,times(1)).update(any(),anyLong());
    }

    @Test
    void deleteAccident() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        mockMvc.perform(delete("/api/accidents/1"))
                .andExpect(status().isOk());
        verify(accidentService,times(1)).delete(anyLong(),anyLong());
    }
}