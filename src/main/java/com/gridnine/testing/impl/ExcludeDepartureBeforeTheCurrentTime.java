package com.gridnine.testing.impl;

import com.gridnine.testing.Filter;
import com.gridnine.testing.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class ExcludeDepartureBeforeTheCurrentTime implements Filter {

    @Override
    public List<Flight> filter(List<Flight> flights) {

        LocalDateTime now = LocalDateTime.now();

        return flights.stream().filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getDepartureDate().isBefore(now)))
                .toList();
    }
}
