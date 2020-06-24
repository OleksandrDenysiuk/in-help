package com.portfolio.inhelp.controller.api;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.service.AccidentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccidentRestController {
    private final AccidentService accidentService;

    public AccidentRestController(AccidentService accidentService) {
        this.accidentService = accidentService;
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
    AccidentDto create(@AuthenticationPrincipal AccountDetails userDetails,
                       @RequestBody AccidentCommand accidentCommand) {
        return accidentService.create(accidentCommand, userDetails.getUserId());
    }

    @PutMapping("/accidents/{accidentId}")
    public @ResponseBody
    AccidentDto update(@AuthenticationPrincipal AccountDetails userDetails,
                       @PathVariable Long accidentId,
                       AccidentCommand accidentCommand) {
        accidentCommand.setId(accidentId);
        return accidentService.update(accidentCommand, userDetails.getUserId());
    }

    @DeleteMapping("/accidents/{accidentId}")
    public @ResponseBody
    void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                @PathVariable Long accidentId) {
        accidentService.delete(accidentId, accountDetails.getUserId());
    }
}
