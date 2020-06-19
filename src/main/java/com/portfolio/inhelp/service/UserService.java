package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getOne(Long id);
    List<UserDto> getAll();
    UserDto create(UserCommand userCommand);
    UserDto update(UserCommand userCommand);
    void delete(Long id);

}
