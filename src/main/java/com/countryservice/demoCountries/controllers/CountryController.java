package com.countryservice.demoCountries.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryservice.demoCountries.beans.Country;
import com.countryservice.demoCountries.services.CountryService;

/**
 * This class will call the CountryService methods
 * @author JMHR
 *
 */

@RestController  //This annotation is used to specify that this class is a controller
public class CountryController {
	
	@Autowired // This annotation allows to all the functions in the CountryService and replace the object creation from the class
				// @Autowired -- is dependency injection
	CountryService countryService;
	
	// /getcountries - is the end point path 
	// http://localhost:8080/getcountries
	@GetMapping("/getcountries")
	public List getCountries() {
		return countryService.getAllCountries();
	}
	
	//http://localhost:8080/getcountries/1
	@GetMapping("/getcountries/{id}")
	public Country getCountryById(@PathVariable(value="id") int id) {
		return countryService.getCountrybyID(id);
	}
	
	// @RequestParam -- is used when will be implemented a query path
	//http://localhost:8080/getcountries/countryname?name=UK
	@GetMapping("/getcountries/countryname")
	public Country getCountryByName(@RequestParam(value="name") String name) {
		return countryService.getCountrybyName(name);
	}	
	
	//@RequestBody -- Is used because the method will receive a JSON parameter to create a new country	
	//http://localhost:8080/addcountry
	//Body::: { "countryName": "Colombia","countryCapital": "Bogotá"}
	@PostMapping("/addcountry")
	public Country addCountry(@RequestBody Country country) {
		return countryService.addCountry(country);
	}
	
	//@RequestBody -- Is used because the method will receive a JSON parameter to update a country
	//http://localhost:8080/updatecountry
	//Body:: { "id": "2", "countryName": "Ecuador","countryCapital": "Quito"}
	@PutMapping("/updatecountry")
	public Country updateCountry(@RequestBody Country country) {
		return countryService.updateCountry(country);
	}
	
	//http://localhost:8080/deletecountry/2
	//Response: {"id": 2, "msg": "Country deleted.."}
	@DeleteMapping("/deletecountry/{id}")
	public AddResponse deleteCountry(@PathVariable(value = "id") int id) {
		return countryService.deleteCountry(id);
	}
}
