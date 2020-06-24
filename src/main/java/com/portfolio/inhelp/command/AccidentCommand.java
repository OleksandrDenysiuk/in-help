package com.portfolio.inhelp.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccidentCommand {

    private Long id;

    @NotBlank(message = "Must be blank")
    @Size(max = 21, message = "Too long, max 21 symbols")
    private String title;

    @NotBlank(message = "Must be blank")
    @Size(max = 1024, message = "Too long, max 1024 symbols")
    private String content;
}
