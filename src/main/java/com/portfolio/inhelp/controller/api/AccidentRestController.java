package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.service.AccidentService;
import com.portfolio.inhelp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccidentRestController {
    private final AccidentService accidentService;
    private final UserService userService;

    public AccidentRestController(AccidentService accidentService, UserService userService) {
        this.accidentService = accidentService;
        this.userService = userService;
    }

    @GetMapping("/accidents")
    public @ResponseBody
    List<AccidentDto> getAll() {
        return accidentService.getAll();
    }

    @GetMapping("/accidents/{accidentId}")
    public @ResponseBody
    AccidentDto getOne(@PathVariable Long accidentId) {
        return accidentService.getOne(accidentId);
    }

    @GetMapping("/users/{userId}/accidents")
    public @ResponseBody
    List<AccidentDto> getAllByUserId(@PathVariable Long userId) {
        return accidentService.getAllByUserId(userId);
    }

    @GetMapping("/users/{userId}/accidents/{accidentId}")
    public @ResponseBody
    AccidentDto getOneByUserId(@PathVariable Long userId,
                               @PathVariable Long accidentId) {
        return accidentService.getOneByUserId(accidentId, userId);
    }

    @PostMapping("/accidents")
    public @ResponseBody
    AccidentDto create(@AuthenticationPrincipal UserDetails userDetails,
                       AccidentCommand accidentCommand) {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            return accidentService.create(accidentCommand, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }

    }

    @PutMapping("/accidents/{accidentId}")
    public @ResponseBody
    AccidentDto update(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable Long accidentId,
                       AccidentCommand accidentCommand) {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            accidentCommand.setId(accidentId);
            return accidentService.update(accidentCommand, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }

    @DeleteMapping("/accidents/{accidentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal UserDetails userDetails,
                @PathVariable Long accidentId) {
        UserDto user = userService.getOneByUsername(userDetails.getUsername());
        if (user != null) {
            accidentService.delete(accidentId, user.getId());
        } else {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
    }
}
