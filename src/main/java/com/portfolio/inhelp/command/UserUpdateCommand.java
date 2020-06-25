package com.portfolio.inhelp.command;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateCommand {

    private Long id;

    @NotBlank
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
