package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    public UserDto registerUserAccount(@Valid @RequestBody UserCommand userCommand){
        UserDto userDto = userService.getOneByUsername(userCommand.getUsername());
        if(userDto != null){
            throw  new RuntimeException("User exists");
        }else {
            return userService.create(userCommand);
        }
    }
}
