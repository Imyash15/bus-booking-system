package com.bookingService.service;

import com.bookingService.exception.ResourceNotFoundException;
import com.bookingService.model.Bus;
import com.bookingService.model.Route;
import com.bookingService.repository.BusRepository;
import com.bookingService.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;

    public Bus addBus(Bus bus)  {

//        Route route = new Route(bus.getRouteFrom(), bus.getRouteTo(), bus.getRouteById().getDistance());
//        if (route == null) throw new ResourceNotFoundException("Route is Not Valid");
//        //adding route for this new bus
//        bus.setRoute(route);
//
//        //adding this  new bus to this route
//        route.getBusList().add(bus);

        Route route = routeRepository.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());

        if (route==null) throw  new ResourceNotFoundException("Route is not valid");

        bus.setRoute(route);

        route.getBusList().add(bus);


        return busRepository.save(bus);
    }

    public Bus getBusById(int busId)  {

        Optional<Bus> busById = busRepository.findById(busId);
        if (busById.isPresent()){
            return busById.get();
        }else
            throw new ResourceNotFoundException("Bus is not available by this Id "+ busId);
    }

    public List<Bus> getAllBus()  {
        List<Bus> allBuses = busRepository.findAll();

        if (allBuses.isEmpty()){
            throw  new ResourceNotFoundException("No Buses Available");

        }else
            return allBuses;
    }

    public Bus updateBus(Bus bus,int busId)  {
        Optional<Bus> existBus = busRepository.findById(busId);

        if (existBus.isEmpty()) throw  new ResourceNotFoundException("Bus is not Available with this bus Id " +  busId);

        Bus newBus = existBus.get();

        Route route = routeRepository.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());

        if (route ==null){
            throw new ResourceNotFoundException("Bus is not available on this route  ");
        }else {
            newBus.setBusName(bus.getBusName());
            newBus.setBusType(bus.getBusType());
            newBus.setDriverName(bus.getDriverName());
            newBus.setBusJourneyDate(bus.getBusJourneyDate());
            newBus.setFare(bus.getFare());
            newBus.setArrivalTime(bus.getArrivalTime());
            newBus.setSeat(bus.getSeat());
            newBus.setDepartureTime(bus.getDepartureTime());
            newBus.setBusJourneyDate(bus.getBusJourneyDate());

            //routeRepository.save(route);
            //newBus.setRoute(route);

            return busRepository.save(newBus);
        }
    }


}
