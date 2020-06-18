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
@Entity(name = "accident")
@Table(name = "accident")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "accident",
                cascade = CascadeType.ALL
    )
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "accident_helper",
                joinColumns = {@JoinColumn(name = "accident_id")},
                inverseJoinColumns = {@JoinColumn(name = "helper_id")}
    )
    private Set<User> helpers = new HashSet<>();

    public void addImage(Image image){
        images.add(image);
        image.setAccident(this);
    }

    public void removeImage(Image image){
        images.remove(image);
        image.setAccident(null);
    }

    public void addHelper(User helper){
        helpers.add(helper);
        helper.addAccident(this);
    }

    public void removeHelper(User helper){
        helpers.remove(helper);
        helper.removeAccident(this);
    }
}
