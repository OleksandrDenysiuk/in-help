package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;

import java.util.List;

public interface AccidentService {
    AccidentDto getOne(Long accidentId);
    AccidentDto getOneByUserId(Long accidentId, Long userId);
    List<AccidentDto> getAll();
    List<AccidentDto> getAllByUserId(Long userId);
    AccidentDto create(AccidentCommand accidentCommand, Long userId);
    AccidentDto update(AccidentCommand accidentCommand, Long userId);
    void delete(Long accidentId, Long userId);
}
