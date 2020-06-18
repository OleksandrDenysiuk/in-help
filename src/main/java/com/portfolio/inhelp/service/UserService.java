package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;

import java.util.Set;

public interface UserService {
    UserDto getOne(Long id);
    Set<UserDto> getAll();
    UserDto create(UserCommand userCommand);
    UserDto update(UserCommand userCommand);
    void delete(Long id);

}
