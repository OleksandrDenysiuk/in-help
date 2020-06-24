package com.portfolio.inhelp.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsCommand {

    private Long id;

    @NotBlank
    @Size(max = 21, message = "Too long, max 32 symbols")
    private String title;

    @NotBlank
    @Size(max = 1024, message = "Too long, max 1024 symbols")
    private String content;
}
