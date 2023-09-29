package com.bookingService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "users_role")
    @Column(name = "role")
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Ticket> ticketList=new ArrayList<>();
}
