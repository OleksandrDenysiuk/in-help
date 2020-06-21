package com.portfolio.inhelp.controller.api;

import com.google.gson.Gson;
import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.mapper.UserMapper;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.service.UserService;
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
class UserRestControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserRestController userRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    void getAll() throws Exception {
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        List<UserDto> list = new ArrayList<>();
        list.add(UserMapper.INSTANCE.toDto(user1));
        list.add(UserMapper.INSTANCE.toDto(user2));

        when(userService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(userService, times(1)).getAll();
    }

    @Test
    void getOne() throws Exception {
        User user1 = User.builder().id(1L).build();

        when(userService.getOne(anyLong())).thenReturn(UserMapper.INSTANCE.toDto(user1));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

        verify(userService, times(1)).getOne(anyLong());
    }

    @Test
    void create() throws Exception {
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername("username");
        Gson gson = new Gson();
        String json = gson.toJson(userCommand);
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex");

        when(userService.create(any())).thenReturn(userDto);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alex"))
                .andReturn();

        verify(userService,times(1)).create(any());
    }

    @Test
    void update() throws Exception {
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername("username");
        Gson gson = new Gson();
        String json = gson.toJson(userCommand);
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex");

        when(userService.update(any())).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alex"))
                .andReturn();

        verify(userService,times(1)).update(any());
    }

    @Test
    void deleteMapping() throws Exception{
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(anyLong());
    }
}