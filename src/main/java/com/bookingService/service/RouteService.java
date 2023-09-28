package com.bookingService.service;

import com.bookingService.exception.ResourceNotFoundException;
import com.bookingService.model.Bus;
import com.bookingService.model.Route;
import com.bookingService.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public Route addRoute(Route route)  {

        Route newRoute = routeRepository.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo());

        if (newRoute!=null) throw new ResourceNotFoundException("Route "+ newRoute.getRouteFrom() +" to "+ newRoute.getRouteTo() +" is already Present ");

        List<Bus> buses= new ArrayList<>();

        if (route!=null){
            route.setBusList(buses);
            return routeRepository.save(route);

        }else {
            throw new ResourceNotFoundException("This route is not Available");
        }
    }

    public Route getRouteById(int routeId) {
       return routeRepository.findById(routeId).orElseThrow(()-> new ResourceNotFoundException("There is no Route Present on this id "+ routeId));
    }
    
    public List<Route> getAllRoute () {
        List<Route> routes = routeRepository.findAll();

        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No Route Available");
        }else
            return routes;

    }

    public Route updateRoute(Route route,int routeId)  {

        Optional<Route> existedRoute = routeRepository.findById(routeId);

        if (existedRoute.isPresent()){
            Route newRoute = existedRoute.get();

            List<Bus> busList = newRoute.getBusList();

            if (!busList.isEmpty()) throw new ResourceNotFoundException("Can't Update RunningRoute ! Buses are already scheduled on this route  ");

            newRoute.setRouteFrom(route.getRouteFrom());
            newRoute.setRouteTo(route.getRouteTo());
            newRoute.setDistance(route.getDistance());

            return routeRepository.save(newRoute);



        }else
            throw  new ResourceNotFoundException("Route doesn't exist on this routeId "+ routeId);

    }
    
    public Route deleteRoute(int routeId) {

        Optional<Route> route = routeRepository.findById(routeId);

        if (route.isPresent()){
            Route existingRoute = route.get();
            routeRepository.delete(existingRoute);

            return existingRoute;
        }else
            throw new ResourceNotFoundException("Route is not available on this routeId "+ routeId);
    }


}
