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

public class ExcludeDepartureBeforeTheCurrentTimeTest {

    private LocalDateTime threeDaysFromNow;
    private Filter filter;

    @BeforeEach
    void setUp() {
        threeDaysFromNow = LocalDateTime.now().plusDays(3);
        filter = new ExcludeDepartureBeforeTheCurrentTime();
    }

    @Test
    void testExcludeDepartureBeforeCurrentTime_PastDeparture() {
        //должны остаться только перелеты без вылета в прошлом
        List<Flight> testFlights = Arrays.asList(
                FlightBuilder.createFlight(
                        threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                FlightBuilder.createFlight(
                        threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.minusDays(4), threeDaysFromNow.plusHours(2)));

        List<Flight> filtered = filter.filter(testFlights);

        assertAll(
                () -> assertFalse(filtered.isEmpty()),
                () -> assertTrue(filtered.stream().noneMatch(flight ->
                        flight.getSegments().stream()
                                .noneMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now()))
                )),
                () -> assertEquals(1, filtered.size()));
    }

    @Test
    void testExcludeDepartureBeforeCurrentTime_FutureDeparture() {
        //перелет только с будущими вылетами
        Flight testFutureFlight = FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2));
        List<Flight> filtered = filter.filter(List.of(testFutureFlight));

        assertEquals(1, filtered.size());
    }

    @Test
    void testExcludeDepartureBeforeCurrentTime_EmptyList() {
        List<Flight> filtered = filter.filter(List.of());

        assertTrue(filtered.isEmpty());
    }
}
