package com.portfolio.inhelp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long id;

    //todo: url to byte[]
    private String url;

    private Long accidentId;

    private Long newsId;
}
