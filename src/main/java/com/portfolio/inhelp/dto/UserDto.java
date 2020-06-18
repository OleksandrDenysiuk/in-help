package com.portfolio.inhelp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String role;

    private Set<AccidentDto> accidents;

    private Set<CommentDto> comments;
}
