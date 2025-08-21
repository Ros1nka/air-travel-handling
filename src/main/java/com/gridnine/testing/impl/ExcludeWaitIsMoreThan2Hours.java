package com.gridnine.testing.impl;

import com.gridnine.testing.Filter;
import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ExcludeWaitIsMoreThan2Hours implements Filter {

    private static final long MAX_WAIT_TIME = 120;

    @Override
    public List<Flight> filter(List<Flight> flights) {

        return flights.stream().filter(flight -> {
            List<Segment> segments = flight.getSegments();

            long totalWaitTime = 0;

            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime arrival = segments.get(i).getArrivalDate();
                LocalDateTime departure = segments.get(i + 1).getDepartureDate();

                Duration duration = Duration.between(arrival, departure);

                if (!duration.isNegative()) {
                    totalWaitTime += Duration.between(arrival, departure).toMinutes();
                }

                if (totalWaitTime > MAX_WAIT_TIME) {
                    return false;
                }
            }
            return true;
        }).toList();
    }
}
