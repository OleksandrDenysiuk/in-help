package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCreateCommand;
import com.portfolio.inhelp.command.UserUpdateCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.model.Role;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.RoleRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getOne() {
        User user = User.builder().id(1L).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDto userDto = userService.getOne(1L);

        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAll() {
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtoList = userService.getAll();

        assertEquals(2, userDtoList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void create() {
        User user = User.builder().id(1L).build();
        Role role = Role.builder().name("USER").build();
        when(userRepository.save(any())).thenReturn(user);
        when(roleRepository.findByName(anyString())).thenReturn(role);

        UserDto userDto = userService.create(UserCreateCommand.builder().id(1L).password("1").build());

        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
    }

    @Test
    void update() {
        User userToUpdate = User.builder().id(1L).username("test").build();
        User userToSave = User.builder().id(1L).username("test").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any())).thenReturn(userToSave);

        UserDto userDto = userService.update(UserUpdateCommand.builder()
                .id(1L)
                .username("username")
                .password("1")
                .firstName("firstName")
                .lastName("lastName")
                .email("mail@mail.com")
                .phoneNumber("1111111111")
                .build());

        assertNotNull(userDto);
        assertEquals("username", userToUpdate.getUsername());
        verify(userRepository,times(1)).findById(anyLong());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void delete() {
        User user = User.builder().id(1L).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository,times(1)).findById(anyLong());
        verify(userRepository,times(1)).delete(any());
    }
}