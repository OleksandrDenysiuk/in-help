package com.portfolio.inhelp.command;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageCommand {

    private Long id;

    private String type;

    private MultipartFile image;
}
