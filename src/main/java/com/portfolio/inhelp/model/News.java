package com.portfolio.inhelp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    private Long id;

    private String title;

    private String content;

    private Set<String> photos;

    private Accident accident;

    private Set<Comment> comments;
}
