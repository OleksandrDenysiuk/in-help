package com.portfolio.inhelp.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCommand {

    private Long id;

    private String content;
}
