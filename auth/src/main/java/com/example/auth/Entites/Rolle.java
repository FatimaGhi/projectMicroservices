package com.example.auth.Entites;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Rolle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "namerolle",unique = true, nullable = false)
    private NameRolle nameRolle;
    @OneToMany
    @JoinColumn(name = "user_id") // foreign key
    private Set<User> users;

}
