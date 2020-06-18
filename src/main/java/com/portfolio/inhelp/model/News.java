package com.portfolio.inhelp.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "news")
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "news",
                cascade = CascadeType.ALL
    )
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "accident_id")
    private Accident accident;

    @OneToMany(mappedBy = "news",
                cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();

    public void addImage(Image image){
        images.add(image);
        image.setNews(this);
    }

    public void removeImage(Image image){
        images.remove(image);
        image.setNews(null);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setNews(this);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setNews(null);
    }
}
