package com.bookingService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private String source;
    private String destination;
    private LocalDate journeyDate;
    private Integer bookedSeat;
    private Integer fare;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Bus bus;

}
