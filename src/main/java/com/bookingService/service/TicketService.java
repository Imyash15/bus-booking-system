package com.bookingService.service;

import com.bookingService.exception.ResourceNotFoundException;
import com.bookingService.model.Bus;
import com.bookingService.model.Ticket;
import com.bookingService.model.User;
import com.bookingService.repository.BusRepository;
import com.bookingService.repository.TicketRepository;
import com.bookingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private UserRepository userRepository;

    public Ticket bookTicket(Ticket ticket,Integer busId ,Integer userId) {

        //Check User exist or not
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id " + userId));

        //Check bus exist or not
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Bus is not found with this id " + busId));

        //Check available seats
        Integer availableSeat = bus.getAvailableSeat();
        if (availableSeat < ticket.getBookedSeat()) throw  new ResourceNotFoundException("Only "+ availableSeat + " seats are available");

        availableSeat -= ticket.getBookedSeat();

        bus.setAvailableSeat(availableSeat);

        //Check Journey date
        if (ticket.getJourneyDate().isBefore(LocalDate.now())) throw new ResourceNotFoundException("Journey Date Should be in future");

        ticket.setStatus("Successful");
        ticket.setDate(LocalDate.now());
        ticket.setTime(LocalTime.now());
        ticket.setBus(bus);
        ticket.setFare(bus.getFare() * ticket.getBookedSeat());
        ticket.setUser(user);

        return ticketRepository.save(ticket);

    }

    public List<Ticket> getAllTicket(){
        List<Ticket> ticketList = ticketRepository.findAll();

        if (ticketList.isEmpty()) throw new ResourceNotFoundException("Tickets not Available");

        return ticketList;
    }

    public Ticket getTicketById(Integer ticketId){

        return ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Tickets not found by this Ticket Id " + ticketId));
    }

    public Ticket updateTicket(Ticket ticket,Integer ticketId){


        Ticket newTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket is not available with this id " + ticketId));
        //find bus
        Integer busId =newTicket.getBus().getBusId();
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Bus not found "));

        Integer availableSeat = bus.getAvailableSeat();

        Integer bookedSeat = ticket.getBookedSeat();
        if (availableSeat < bookedSeat) throw  new ResourceNotFoundException("Only "+ availableSeat + " seats are available");

        availableSeat -= bookedSeat;

        bus.setAvailableSeat(availableSeat);


        newTicket.setDate(LocalDate.now());
        newTicket.setTime(LocalTime.now());
        newTicket.setJourneyDate(ticket.getJourneyDate());
        newTicket.setBookedSeat(bookedSeat);
        newTicket.setFare(bus.getFare() * bookedSeat);
        newTicket.setSource(ticket.getSource());
        newTicket.setDestination(ticket.getDestination());
        newTicket.setBus(bus);

        return ticketRepository.save(newTicket);

    }
}