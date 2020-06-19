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
@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String role;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL
    )
    private Set<Accident> accidents = new HashSet<>();

    @OneToMany(mappedBy = "author",
                cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();

    public void addAccident(Accident accident){
        accidents.add(accident);
        accident.setUser(this);
    }

    public void removeAccident(Accident accident){
        accidents.remove(accident);
        accident.setUser(null);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
