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

public class ExcludeWaitIsMoreThan2HoursTest {

    private LocalDateTime threeDaysFromNow;
    private Filter filter;

    @BeforeEach
    void setUp() {
        threeDaysFromNow = LocalDateTime.now().plusDays(3);
        filter = new ExcludeWaitIsMoreThan2Hours();
    }

    @Test
    void testExcludeArrivalBeforeDeparture_PastDeparture() {
        //должнен быть исключен перелёт долгой пересадкой
        List<Flight> testFlights = Arrays.asList(
                FlightBuilder.createFlight(
                        threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(4), threeDaysFromNow.plusDays(2)),
                FlightBuilder.createFlight(
                        threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(8)));

        List<Flight> filtered = filter.filter(testFlights);

        assertAll(
                () -> assertFalse(filtered.isEmpty()),
                () -> assertEquals(1, filtered.size()));
    }
}

