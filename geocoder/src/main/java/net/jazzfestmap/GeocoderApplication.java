package net.jazzfestmap;

import net.jazzfestmap.geocoder.dao.GeocodeDao;
import net.jazzfestmap.geocoder.service.GeocodeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class GeocoderApplication implements CommandLineRunner {

	@Inject
	private GeocodeService geocodeService;

	@Inject
	private GeocodeDao geocodeDao;


	public static void main(String[] args) {
		SpringApplication.run(GeocoderApplication.class, args);
	}

	/**
	 * Заполнения БД в консольном режиме.
	 * Для отладки и быстрого прототипа
	 * @param strings
	 * @throws Exception
	 */
	@Override
	public void run(String... strings) throws Exception {
		if (strings.length > 0 && strings[0].equals("cli")) {
			System.out.println("GeocoderApplication Console STARTED");
			Collection<String> cities = geocodeDao.getCityNames();
			geocodeDao.saveCityCoords(geocodeService.geocode(cities));
		}
	}
}
