package com.portfolio.inhelp.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateCommand {
    private Long id;

    @NotBlank
    @Size(max = 32, message = "Too long, max 32 symbols")
    private String username;

    @NotBlank
    @Size(max = 32, message = "Too long, max 32 symbols")
    private String password;

}
