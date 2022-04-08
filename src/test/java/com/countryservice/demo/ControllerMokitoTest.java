package com.countryservice.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class) // It used to execute The methods in order
@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ControllerMokitoTest {

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	List<Country> mycountries;
	Country country;
	
	@Test
	@Order(1)
	public void test_getAllCountries(){
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"India", "Delhi"));
		mycountries.add(new Country(2,"USA", "Washington"));
		
		when(countryService.getAllCountries()).thenReturn(mycountries);
		ResponseEntity<List<Country>> res= countryController.getCountries();
		Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
		Assertions.assertEquals(2,res.getBody().size());
		
		}
	
	@Test
	@Order(2)
	public void test_get_country_by_id() {
		country = new Country(2, "USA", "Washington");
		int countryID = 2;

		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryById(countryID);

		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(countryID, res.getBody().getId());
	}

	@Test
	@Order(3)
	public void test_get_Country_By_Name() {
		country = new Country(2, "USA", "Washington");
		String countryName = "USA";

		when(countryService.getCountrybyName(countryName)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByName(countryName);

		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(countryName, res.getBody().getCountryName());

	}

	@Test
	@Order(4)
	public void test_add_Country() {
		country = new Country(2, "Germany", "Berlin");

		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);

		Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());
		Assertions.assertEquals(country, res.getBody());

	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		country = new Country(3, "Japan", "Tokyo");
		int countryID = 3;

		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);

		ResponseEntity<Country> res = countryController.updateCountry(countryID, country);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(3, res.getBody().getId());
		Assertions.assertEquals("Japan", res.getBody().getCountryName());
		Assertions.assertEquals("Tokyo", res.getBody().getCountryCapital());
	}

	@Test
	@Order(6)
	public void test_deleteCountry() {
		country = new Country(3, "Japan", "Tokio");
		int countryID = 3;

		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.deleteCountry(countryID);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
}
