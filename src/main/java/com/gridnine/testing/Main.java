package com.gridnine.testing;

import com.gridnine.testing.impl.ExcludeArrivalBeforeDeparture;
import com.gridnine.testing.impl.ExcludeDepartureBeforeTheCurrentTime;
import com.gridnine.testing.impl.ExcludeWaitIsMoreThan2Hours;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		List<Flight> flights = FlightBuilder.createFlights();

		System.out.println("\n" + "Все перелёты:");
		printFlights(flights);
		System.out.println();

		Filter[] filters = {
				new ExcludeDepartureBeforeTheCurrentTime(),
				new ExcludeArrivalBeforeDeparture(),
				new ExcludeWaitIsMoreThan2Hours()
		};

		String[] filterNames = {
				"Исключить вылет до текущего момента",
				"Исключить перелеты с прибытием до вылета",
				"Исключить перелеты с ожиданием > 2 часов"
		};

		for (int i = 0; i < filters.length; i++) {
			List<Flight> filteredFlights = filters[i].filter(flights);

			System.out.println(filterNames[i]);
			printFlights(filteredFlights);
			System.out.println();
		}
	}

	private static void printFlights(List<Flight> flights) {
		if (flights.isEmpty()) {
			System.out.println("Нет перелетов");
			return;
		}
		for (Flight flight : flights) {
			System.out.println("Перелет :"  + flight);
		}
		System.out.println("Всего: " + flights.size() + " перелетов");
	}
}
