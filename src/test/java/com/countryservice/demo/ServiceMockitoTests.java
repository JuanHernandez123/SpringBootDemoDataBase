package com.countryservice.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

/*
 * The Idea for this class is to create unit testing for mock the CountryService and CountryRepository functions.
 * Because the CountryController functions depends of those classes. */

@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ServiceMockitoTests {
	
	//This is the mock for the class who talk with the database
	@Mock
	CountryRepository countryrep;
	
	//This is the mock for the countryService functions which use the countryrep
	@InjectMocks
	CountryService countryService;
	
	public List<Country> mycountries;
	
	@Test
	@Order(1)
	public void test_getAllCountries() {
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"India","Delhi"));
		mycountries.add(new Country(2,"Colombia","Bogota"));
				
		//Basically, it means, when countryrep.findAll() function is called then, will be return the mycountries object created above
		//findAll() function is called inside getAllCountries() for that reason is mocked 
		
		when(countryrep.findAll()).thenReturn(mycountries);
		countryService.getAllCountries();
		
		Assertions.assertEquals(2,countryService.getAllCountries().size());
	}
}
