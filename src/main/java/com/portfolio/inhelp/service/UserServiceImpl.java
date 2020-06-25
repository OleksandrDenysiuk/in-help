package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.UserCreateCommand;
import com.portfolio.inhelp.command.UserUpdateCommand;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.exception.UserNotFoundException;
import com.portfolio.inhelp.mapper.UserMapper;
import com.portfolio.inhelp.model.AccountDetails;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.RoleRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto getOne(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDto getOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper.INSTANCE::toDto)
                .orElse(null);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserCreateCommand userCreateCommand) {
        Optional<User> optionalUser = userRepository.findByUsername(userCreateCommand.getUsername());
        if (optionalUser.isEmpty()) {
            User user = User.builder()
                    .id(userCreateCommand.getId())
                    .username(userCreateCommand.getUsername())
                    .password(new BCryptPasswordEncoder().encode(userCreateCommand.getPassword()))
                    .roles(new HashSet<>())
                    .build();
            user.getRoles().add(roleRepository.findByName("USER"));
            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        } else {
            throw new RuntimeException("User exist");
        }
    }

    @Override
    public UserDto update(UserUpdateCommand userUpdateCommand) {
        Optional<User> optionalUser = userRepository.findById(userUpdateCommand.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setUsername(userUpdateCommand.getUsername());

            if(!userUpdateCommand.getPassword().equals("") || userUpdateCommand.getPassword() != null) {
                user.setPassword(new BCryptPasswordEncoder().encode(userUpdateCommand.getPassword()));
            }

            user.setFirstName(userUpdateCommand.getFirstName());
            user.setLastName(userUpdateCommand.getLastName());
            user.setEmail(userUpdateCommand.getEmail());
            user.setPhoneNumber(userUpdateCommand.getPhoneNumber());

            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        } else {
            throw new UserNotFoundException(userUpdateCommand.getId());
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else {
            User user = optionalUser.get();
            return new AccountDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
        }
    }

}
