package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.mapper.UserMapper;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getOne(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return UserMapper.INSTANCE.toDto(optionalUser.get());
        } else {
            throw new RuntimeException("User not Found");
        }
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserCommand userCommand) {
        User user = User.builder()
                .id(userCommand.getId())
                .username(userCommand.getUsername())
                .password(userCommand.getPassword())
                .avatar(userCommand.getAvatar())
                .firstName(userCommand.getFirstName())
                .lastName(userCommand.getLastName())
                .phoneNumber(userCommand.getPhoneNumber())
                .email(userCommand.getEmail())
                .build();
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }

    @Override
    public UserDto update(UserCommand userCommand) {
        Optional<User> optionalUser = userRepository.findById(userCommand.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userCommand.getUsername());
            user.setPassword(userCommand.getPassword());
            user.setAvatar(userCommand.getAvatar());
            user.setFirstName(userCommand.getFirstName());
            user.setLastName(userCommand.getLastName());
            user.setPhoneNumber(userCommand.getPhoneNumber());
            user.setEmail(userCommand.getEmail());
            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
