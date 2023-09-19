package com.bookingService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busId;
    private String busName;
    private String driverName;
    private String busType;
    private String routeFrom;
    private String routeTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate busJourneyDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private  LocalTime departureTime;

    @Column(name = "total_seat")
    private Integer seat;

    private Integer availableSeat;

    private Integer fare;

    @ManyToOne(cascade = CascadeType.ALL)
    private Route route;

    @OneToMany(mappedBy = "bus",cascade = CascadeType.ALL)
    private List<Ticket> ticketList=new ArrayList<>();

  }
