package com.countryservice.demo;

import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 *This class was created to execute the unit test using the Url 
 * 
 */

@TestMethodOrder(OrderAnnotation.class) // It used to execute The methods in order
@ComponentScan(basePackages = "com.restservices.demo") // This annotation means, when the base package is executed the
														// urls will be
//available to test the services, also, referes the location for the main class to execute the project. 
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMvcTest.class })
public class ControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;
	
	List<Country> mycountries;
	Country country;

	/**
	 * This function is used to inject all the functions in country controller and
	 * access to those functions through url's for that reason is necessary execute
	 * it before each test
	 */
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception {
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"India", "Delhi"));
		mycountries.add(new Country(2, "USA", "Washington"));
		
		when(countryService.getAllCountries()).thenReturn(mycountries);
		
		this.mockMvc.perform(get("/getcountries"))
			.andExpect(status().isFound())
			.andDo(print()); // Print function allows see all the information sent in the request
		
	}
	
	@Test
	@Order(2)
	public void test_getCountrybyID() throws Exception{
		country = new Country(2,"USA", "Washington");
		int countryID=2;
		
		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		
		this.mockMvc.perform(get("/getcountries/{id}", countryID))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
		.andDo(print());
	}
	
	
	
	@Test
	@Order(3)
	public void test_getCountrybyName() throws Exception{
		country = new Country(2,"USA", "Washington");
		String countryName="USA";
		
		when(countryService.getCountrybyName(countryName)).thenReturn(country);
		
		this.mockMvc.perform(get("/getcountries/countryname").param("name", "USA"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
		.andDo(print());	
		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception{
		country = new Country(3,"Germany", "Berlin"); 	 	
		
		when(countryService.addCountry(country)).thenReturn(country); 
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody= mapper.writeValueAsString(country); // It's used to convert the object to String
		
		this.mockMvc.perform(post("/addcountry")
					.content(jsonbody)
					.contentType(MediaType.APPLICATION_JSON)) //Here converts the string to object, because the service receives a JSON
					.andExpect(status().isCreated())
					.andDo(print());				
	
	}
	
	@Test
	@Order(5)
	public void test_updatedCountry() throws Exception {
		country = new Country(3,"Japan", "Tokyo");
		int countryID=3;
		
		when(countryService.getCountrybyID(countryID)).thenReturn(country); 
		when(countryService.updateCountry(country)).thenReturn(country); 
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody= mapper.writeValueAsString(country); // It's used to convert the object to String
		
		this.mockMvc.perform(put("/updatecountry/{id}", countryID)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)) //Here converts the string to object, because the service receives a JSON
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
				.andDo(print());		
		
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception{
		country = new Country(3,"Japan", "Tokyo");
		int countryID=3;
		
		when(countryService.getCountrybyID(countryID)).thenReturn(country); 
		
		this.mockMvc.perform(delete("/deletecountry/{id}", countryID))		
				.andExpect(status().isOk());		
	}

}
