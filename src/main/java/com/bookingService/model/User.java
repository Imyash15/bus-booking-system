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

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Ticket> ticketList=new ArrayList<>();

}
