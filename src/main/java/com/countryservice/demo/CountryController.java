package com.countryservice.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class will call the CountryService methods
 * 
 * @author JMHR
 *
 */

@RestController // This annotation is used to specify that this class is a controller
public class CountryController {

	@Autowired // This annotation allows to all the functions in the CountryService and replace
				// the object creation from the class
				// @Autowired -- is dependency injection
	CountryService countryService;


	// /getcountries - is the end point path
	// http://localhost:8080/getcountries
	@GetMapping("/getcountries")
	public List<Country> getCountries() {
		return countryService.getAllCountries();
	}

	// http://localhost:8080/getcountries/1
	@GetMapping("/getcountries/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id) {
		try {
			Country country = countryService.getCountrybyID(id);
			// ResponseEntity -- It's used to validate if the id exist in the database,
			// because if the id not exist
			// an exception will be executed and this code is implemented to handle it.
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// @RequestParam -- is used when will be implemented a query path
	// http://localhost:8080/getcountries/countryname?name=UK
	@GetMapping("/getcountries/countryname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String name) {
		try {
			Country country = countryService.getCountrybyName(name);
			// ResponseEntity -- It's used to validate if the name exist in the database,
			// because if the name not exist
			// an exception will be executed and this code is implemented to handle it.
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// @RequestBody -- Is used because the method will receive a JSON parameter to
	// create a new country
	// http://localhost:8080/addcountry
	// Body::: { "countryName": "Colombia","countryCapital": "Bogotá"}
	@PostMapping("/addcountry")
	public Country addCountry(@RequestBody Country country) {
		return countryService.addCountry(country);
	}

	// @RequestBody -- Is used because the method will receive a JSON parameter to
	// update a country
	// http://localhost:8080/updatecountry
	// Body:: { "id": "2", "countryName": "Ecuador","countryCapital": "Quito"}
	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country) {

		try {
			// This code is used to validate if the country id exist in the data base
			Country existCountry = countryService.getCountrybyID(id);

			// Set values using the POJO/Bean Class
			existCountry.setCountryName(country.getCountryName());
			existCountry.setCountryCapital(country.getCountryCapital());

			Country updated_country = countryService.updateCountry(existCountry);
			return new ResponseEntity<Country>(updated_country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// http://localhost:8080/deletecountry/2
	// Response: {"id": 2, "msg": "Country deleted.."}
	@DeleteMapping("/deletecountry/{id}")
	public AddResponse deleteCountry(@PathVariable(value = "id") int id) {
		return countryService.deleteCountry(id);
	}
}
