package com.bookingService.controller;

import com.bookingService.exception.GeneralResponse;
import com.bookingService.model.Bus;
import com.bookingService.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BusController {
    @Autowired
    private BusService busService;
    @PostMapping("/bus/add")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus)  {
        Bus newBus = busService.addBus(bus);

        return  new ResponseEntity<>(newBus, HttpStatus.CREATED);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<GeneralResponse> getBusById(@PathVariable Integer busId)  {

        Bus bus = busService.getBusById(busId);
        return ResponseEntity.ok(new GeneralResponse("Successfully Fetched ",true,bus));
    }

    @GetMapping("/bus/all")
    public ResponseEntity<List<Bus>> getAllBuses()  {

        List<Bus> allBus = busService.getAllBus();
        return ResponseEntity.ok(allBus);
    }

    @PutMapping("/bus/update/{busId}")
    public ResponseEntity<GeneralResponse> updateBus(@RequestBody Bus bus,@PathVariable Integer busId)  {
        Bus updatedBus = busService.updateBus(bus, busId);

        return ResponseEntity.ok(new GeneralResponse("Update Successfully ",true,updatedBus));
    }
}

