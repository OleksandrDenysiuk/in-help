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
public class Accident {

    private Long id;

    private String title;

    private String content;

    private Set<String> portfolio;

    private Set<String> documents;

    private User inNeed;

    private Set<User> helpers;
}
