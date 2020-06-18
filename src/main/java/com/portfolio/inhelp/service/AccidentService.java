package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;

import java.util.Set;

public interface AccidentService {
    AccidentDto getOne(Long accidentId);
    Set<AccidentDto> getAll();
    AccidentDto create(AccidentCommand accidentCommand);
    AccidentDto update(AccidentCommand accidentCommand);
    void delete(Long accidentId);
}
