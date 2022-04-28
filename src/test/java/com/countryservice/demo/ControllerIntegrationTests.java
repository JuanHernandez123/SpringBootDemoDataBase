package com.countryservice.demo;


import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;

/*
 * This class was made to test the integration between the services and H2 DB when the application is running.
 * 
 * For execute this test cases in a correct way:
 * 1. Ececute the SprintBootapplication (SpringBootCountryServiceProjectApplication)
 * 2. Execute this class with junit
 */

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {
	
	@Test
	@Order(1)
	public void getAllCountriesIntegrationTest() throws JSONException{
		String expected = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"USA\",\r\n"
				+ "        \"countryCapital\": \"Washington\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		ResponseEntity<String> response = resTemplate.getForEntity("http://localhost:8080/getcountries/", String.class);
		System.out.println(response.getClass());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(),false);//false means that avoid the spaces. Strict checks all including spaces		
	}
	
	
	@Test
	@Order(2)
	public void getCountryByIDIntegration() throws JSONException {
		
		String expected = "{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"countryName\": \"India\",\r\n"
				+ "    \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		ResponseEntity<String> response = resTemplate.getForEntity("http://localhost:8080/getcountries/1", String.class);// getForEntity because is a Get
		System.out.println(response.getClass());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(),false);//false means that avoid the spaces. Strict checks all including spaces		
	}
	
	@Test
	@Order(3)
	public void getCountryByNameIntegrationTest() throws JSONException {
		
		String expected= "{\r\n"
				+ "    \"id\": 2,\r\n"
				+ "    \"countryName\": \"USA\",\r\n"
				+ "    \"countryCapital\": \"Washington\"\r\n"
				+ "}";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		ResponseEntity<String> response = resTemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=USA", String.class);// getForEntity because is a Get
		System.out.println(response.getClass());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(),false);//false means that avoid the spaces. Strict checks all including spaces		
	
	}
	
	@Test
	@Order(4)
	public void addCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(3, "Germany", "Berlin");
		
		String expected = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Germany\",\r\n"
				+ "    \"countryCapital\": \"Berlin\"\r\n"
				+ "}";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response = resTemplate.postForEntity("http://localhost:8080/addcountry",request, String.class);// postForEntity because is post
		System.out.println(response.getBody());
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	@Test
	@Order(5)
	public void updateCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "Japan", "Tokyo");
		
		String expected = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n"
				+ "}";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response = resTemplate.exchange("http://localhost:8080/updatecountry/3",HttpMethod.PUT, request, String.class);// postForEntity because is post
		System.out.println(response.getBody());
		
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	@Test
	@Order(6)
	public void deleteCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(3, "Japan", "Tokyo");
		
		String expected = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n"
				+ "}";
		
		TestRestTemplate resTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response = resTemplate.exchange("http://localhost:8080/deletecountry/3",HttpMethod.DELETE, request, String.class);// postForEntity because is post
		System.out.println(response.getBody());
		
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
}
