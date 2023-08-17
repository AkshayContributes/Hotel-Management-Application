package com.user.service.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name= "id")
    private String userId;

    @Column(name= "name", length = 20)
    private String name;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "about", length = 100)
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();
}
