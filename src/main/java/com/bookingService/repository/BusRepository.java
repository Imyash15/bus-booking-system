package com.bookingService.repository;

import com.bookingService.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus,Integer> {
    public Bus findByRouteFromAndRouteTo(String routeFrom, String routeTo);
}
