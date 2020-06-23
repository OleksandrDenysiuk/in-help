package com.portfolio.inhelp.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCommand {
    private Long id;

    private String username;

    private String password;

}
