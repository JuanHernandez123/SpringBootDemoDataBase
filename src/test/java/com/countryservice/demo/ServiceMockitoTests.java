package com.countryservice.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

/*
 * The Idea for this class is to create unit testing for mock the CountryService and CountryRepository functions.
 * Because the CountryController functions depends of those classes. */


@TestMethodOrder(OrderAnnotation.class) // It used to execute The methods in order
@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ServiceMockitoTests {

	// This is the mock for the class who talk with the database
	@Mock
	CountryRepository countryrep;

	// This is the mock for the countryService functions which use the countryrep
	@InjectMocks
	CountryService countryService;

	public List<Country> mycountries;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Colombia", "Bogota"));

		// Basically, it means, when countryrep.findAll() function is called then, will
		// be return the mycountries object created above
		// findAll() function is called inside getAllCountries() for that reason is
		// mocked

		when(countryrep.findAll()).thenReturn(mycountries);
		countryService.getAllCountries();
		Assertions.assertEquals(2, countryService.getAllCountries().size());
	}

	@Test
	@Order(2)
	public void test_getCountry_By_ID() {
		List<Country> mycountriees = new ArrayList<Country>();
		mycountriees.add(new Country(1, "India", "Delhi"));
		mycountriees.add(new Country(2, "USA", "Washington"));

		int countryID = 1;
		when(countryrep.findAll()).thenReturn(mycountriees); // Mocking
		Assertions.assertEquals(countryID, countryService.getCountrybyID(countryID).getId());

	}

	@Test
	@Order(3)
	public void test_getCountry_By_Name() {
		List<Country> mycountriees = new ArrayList<Country>();
		mycountriees.add(new Country(1, "India", "Delhi"));
		mycountriees.add(new Country(2, "USA", "Washington"));

		String countryName = "India";
		when(countryrep.findAll()).thenReturn(mycountriees); // Mocking
		Assertions.assertEquals(countryName, countryService.getCountrybyName(countryName).getCountryName());
	}

	@Test
	@Order(4)
	public void test_Add_Country() {
		Country country = new Country(1, "Germany", "Berlin");

		when(countryrep.save(country)).thenReturn(country);
		Assertions.assertEquals(country, countryService.addCountry(country));
	}

	@Test
	@Order(5)
	public void test_Update_Country() {
		Country country = new Country(3, "Germany", "Berlin");

		when(countryrep.save(country)).thenReturn(country);
		Assertions.assertEquals(country, countryService.updateCountry(country));
	}
	
	@Test
	@Order(6)
	public void test_Delete_Country() {
		Country country = new Country(3, "Germany", "Berlin");
		countryService.deleteCountry(country);
		verify(countryrep,times(1)).delete(country);
		
	}
	

}
