package com.bookingService.controller;

import com.bookingService.exception.GeneralResponse;
import com.bookingService.model.Ticket;
import com.bookingService.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/user/{userId}/bus/{busId}/ticket/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket, @PathVariable Integer busId,@PathVariable Integer userId){
        Ticket bookedTicket = ticketService.bookTicket(ticket, busId, userId);

        return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);

    }

    @GetMapping("/ticket/all")
    public ResponseEntity<List<Ticket>> getAllTickets(){

        List<Ticket> allTicket = ticketService.getAllTicket();

        return ResponseEntity.ok(allTicket);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<GeneralResponse> getTicketById(@PathVariable Integer ticketId){
        Ticket ticketById = ticketService.getTicketById(ticketId);

        return ResponseEntity.ok(new GeneralResponse("Successfully Fetched",true,ticketById));
    }

    @PutMapping("/ticket/update/{ticketId}")
    public ResponseEntity<GeneralResponse> updateTicket(@RequestBody Ticket ticket,@PathVariable Integer ticketId){

        Ticket updatedTicket = ticketService.updateTicket(ticket, ticketId);

        return ResponseEntity.ok(new GeneralResponse("Updated Successfully", true,updatedTicket));
    }


    @GetMapping("/ticket/user/{userId}")
    public ResponseEntity<GeneralResponse> getByTicketByUserId(@PathVariable Integer userId){
        List<Ticket> byUserUserId = ticketService.getByUserUserId(userId);

        return ResponseEntity.ok(new GeneralResponse("Successfully Fetched By UserID "+ userId ,true,byUserUserId));
    }


    @DeleteMapping("/ticket/delete/{ticketId}")
    public ResponseEntity<GeneralResponse> deleteTicket(@PathVariable Integer ticketId) {

        Ticket ticket = ticketService.deleteTicket(ticketId);

        return ResponseEntity.ok(new GeneralResponse("Ticket Deleted Successfully",true,ticket));
    }

}
