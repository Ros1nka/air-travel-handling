package com.gridnine.testing.impl;

import com.gridnine.testing.Filter;
import com.gridnine.testing.Flight;

import java.util.List;

public class ExcludeArrivalBeforeDeparture implements Filter {

    @Override
    public List<Flight> filter(List<Flight> flights) {

        return flights.stream().filter
                        (flight -> flight.getSegments().stream()
                                .noneMatch(segment -> segment.getDepartureDate()
                                        .isAfter(segment.getArrivalDate())))
                .toList();
    }
}
