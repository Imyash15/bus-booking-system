package com.bookingService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer routeId;
    private String routeFrom;
    private String routeTo;
    private Integer distance;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL)
    private List<Bus> busList=new ArrayList<>();



}