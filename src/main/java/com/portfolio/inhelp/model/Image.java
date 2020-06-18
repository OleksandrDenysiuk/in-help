package com.portfolio.inhelp.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "image")
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String url;

    @ManyToOne
    @JoinColumn(name = "accident_id")
    private Accident accident;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
}
