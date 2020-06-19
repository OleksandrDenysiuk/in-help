package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.mapper.AccidentMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final UserRepository userRepository;

    public AccidentServiceImpl(AccidentRepository accidentRepository, UserRepository userRepository) {
        this.accidentRepository = accidentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccidentDto getOne(Long accidentId) {
        Optional<Accident> optionalAccident = accidentRepository.findById(accidentId);
        if (optionalAccident.isPresent()) {
            return AccidentMapper.INSTANCE.toDto(optionalAccident.get());
        } else {
            throw new RuntimeException("Accident not Found");
        }
    }

    @Override
    public AccidentDto getOneByUserId(Long accidentId, Long userId) {

        Accident accident = accidentRepository.findByIdAndUserId(accidentId, userId);

        if (accident != null) {
            return AccidentMapper.INSTANCE.toDto(accident);
        } else {
            throw new RuntimeException("Accident not Found");
        }

    }

    @Override
    public List<AccidentDto> getAll() {
        return accidentRepository.findAll().stream()
                .map(AccidentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccidentDto> getAllByUserId(Long userId) {
        return accidentRepository.findAllByUserId(userId).stream()
                .map(AccidentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccidentDto create(AccidentCommand accidentCommand, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            Accident accident = Accident.builder()
                    .title(accidentCommand.getTitle())
                    .content(accidentCommand.getContent())
                    .build();
            optionalUser.get().addAccident(accident);
            return AccidentMapper.INSTANCE.toDto(accidentRepository.save(accident));
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public AccidentDto update(AccidentCommand accidentCommand, Long userId) {
        Accident accident = accidentRepository.findByIdAndUserId(accidentCommand.getId(), userId);

        if (accident != null) {
            accident.setTitle(accidentCommand.getTitle());
            accident.setContent(accidentCommand.getContent());
            return AccidentMapper.INSTANCE.toDto(accidentRepository.save(accident));
        } else {
            throw new RuntimeException("Accident not Found");
        }

    }

    @Override
    public void delete(Long accidentId, Long userId) {
        Accident accident = accidentRepository.findByIdAndUserId(accidentId,userId);
        if (accident != null) {
            accidentRepository.deleteById(accidentId);
        } else {
            throw new RuntimeException("Accident not found");
        }
    }
}
