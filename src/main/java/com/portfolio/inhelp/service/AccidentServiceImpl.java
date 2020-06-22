package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.exception.AccidentNotFoundException;
import com.portfolio.inhelp.exception.UserNotFoundException;
import com.portfolio.inhelp.mapper.AccidentMapper;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final UserRepository userRepository;

    public AccidentServiceImpl(AccidentRepository accidentRepository, UserRepository userRepository) {
        this.accidentRepository = accidentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccidentDto getOne(Long accidentId) {
        return accidentRepository.findById(accidentId)
                .map(AccidentMapper.INSTANCE::toDto)
                .orElseThrow(() -> new AccidentNotFoundException(accidentId));
    }

    @Override
    public AccidentDto getOneByUserId(Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst()
                    .map(AccidentMapper.INSTANCE::toDto)
                    .orElseThrow(() -> new AccidentNotFoundException(accidentId));
        } else {
            throw new UserNotFoundException(userId);
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
        if (optionalUser.isPresent()) {
            Accident accident = Accident.builder()
                    .title(accidentCommand.getTitle())
                    .content(accidentCommand.getContent())
                    .build();
            optionalUser.get().addAccident(accident);
            return AccidentMapper.INSTANCE.toDto(accidentRepository.save(accident));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public AccidentDto update(AccidentCommand accidentCommand, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Accident> optionalAccident = optionalUser.get()
                    .getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentCommand.getId()))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                accident.setTitle(accidentCommand.getTitle());
                accident.setContent(accidentCommand.getContent());
                return AccidentMapper.INSTANCE.toDto(accidentRepository.save(accident));
            } else {
                throw new AccidentNotFoundException(accidentCommand.getId());
            }
        } else {
            throw new UserNotFoundException(userId);
        }

    }

    @Override
    public void delete(Long accidentId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Accident> optionalAccident = user
                    .getAccidents().stream()
                    .filter(accident -> accident.getId().equals(accidentId))
                    .findFirst();
            if (optionalAccident.isPresent()) {
                Accident accident = optionalAccident.get();
                user.removeAccident(accident);
                accidentRepository.delete(optionalAccident.get());
            } else {
                throw new AccidentNotFoundException(accidentId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}
