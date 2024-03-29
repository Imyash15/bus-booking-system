package com.bookingService.controller;

import com.bookingService.exception.GeneralResponse;
import com.bookingService.model.Route;
import com.bookingService.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping("/route/add")
    public ResponseEntity<Route> addRoute(@RequestBody Route route){
        Route newRoute = routeService.addRoute(route);
        return new ResponseEntity<>(route, HttpStatus.CREATED);
    }

    @PutMapping("/route/update/{routeId}")
    public ResponseEntity<GeneralResponse> updateRoute(@RequestBody Route route,@PathVariable Integer routeId){
        Route updatedRoute = routeService.updateRoute(route, routeId);
        return ResponseEntity.ok(new GeneralResponse("Updated Successfully",true,updatedRoute));
    }

    @GetMapping("/route/all")
    public ResponseEntity<List<Route>> getAllRoutes(){
        List<Route> allRoute = routeService.getAllRoute();
        return ResponseEntity.ok(allRoute);
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<GeneralResponse> getRouteById(@PathVariable Integer routeId){
        Route route = routeService.getRouteById(routeId);
        return ResponseEntity.ok(new GeneralResponse("Fetched Successfully",true,route));
    }

    @DeleteMapping("/route/delete/{routeId}")
    public ResponseEntity<GeneralResponse> deleteRoute(@PathVariable Integer routeId){
        Route deleteRoute = routeService.deleteRoute(routeId);
        return ResponseEntity.ok(new GeneralResponse("Deleted Successfully",true,deleteRoute));
    }
}
