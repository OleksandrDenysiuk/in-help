package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public @ResponseBody
    List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public @ResponseBody
    UserDto getOne(@PathVariable Long userId){
        return userService.getOne(userId);
    }

    @PostMapping("/users")
    public @ResponseBody
    UserDto create(@Valid @RequestBody UserCommand userCommand){
        return userService.create(userCommand);
    }

    @PutMapping("/users/{userId}")
    public @ResponseBody
    UserDto update(@PathVariable Long userId,
                   @Valid @RequestBody UserCommand userCommand){
        userCommand.setId(userId);
        return userService.update(userCommand);
    }

    @DeleteMapping("/users/{userId}")
    public @ResponseBody void delete(@PathVariable Long userId){
        userService.delete(userId);
    }
}
