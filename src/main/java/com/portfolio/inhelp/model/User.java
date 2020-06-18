package com.portfolio.inhelp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();
}
