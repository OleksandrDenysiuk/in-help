package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCreateCommand;
import com.portfolio.inhelp.command.UserUpdateCommand;
import com.portfolio.inhelp.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto getOne(Long id);
    List<UserDto> getAll();
    UserDto getOneByUsername(String username);
    UserDto create(UserCreateCommand userCreateCommand);
    UserDto update(UserUpdateCommand userUpdateCommand);
    void delete(Long id);

}
