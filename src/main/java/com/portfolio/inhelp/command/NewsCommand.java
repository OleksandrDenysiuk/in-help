package com.portfolio.inhelp.command;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsCommand {

    private Long id;

    private String title;

    private String content;

    private MultipartFile images;
}
