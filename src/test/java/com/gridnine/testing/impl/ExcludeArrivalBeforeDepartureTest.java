package com.gridnine.testing.impl;

import com.gridnine.testing.Filter;
import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExcludeArrivalBeforeDepartureTest {

    private LocalDateTime threeDaysFromNow;
    private Filter filter;

    @BeforeEach
    void setUp() {
        threeDaysFromNow = LocalDateTime.now().plusDays(3);
        filter = new ExcludeArrivalBeforeDeparture();
    }

    @Test
    void testExcludeArrivalBeforeDeparture_PastDeparture() {
        //должнен быть исключен перелёт с некорректной датой
        List<Flight> testFlights = Arrays.asList(
                FlightBuilder.createFlight(
                threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow, threeDaysFromNow.plusDays(2)),
        FlightBuilder.createFlight(
                threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow, threeDaysFromNow.minusHours(2)));

        List<Flight> filtered = filter.filter(testFlights);

        assertAll(
                () -> assertFalse(filtered.isEmpty()),
                () -> assertEquals(1, filtered.size()));
    }
}
