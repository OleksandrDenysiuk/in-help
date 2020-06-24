package com.portfolio.inhelp.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCommand {

    private Long id;

    @NotBlank
    @Size(max = 32, message = "Too long, max 32 symbols")
    private String content;
}
