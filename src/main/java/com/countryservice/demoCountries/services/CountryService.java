package com.countryservice.demoCountries.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryservice.demoCountries.beans.Country;
import com.countryservice.demoCountries.controllers.AddResponse;
import com.countryservice.demoCountries.repositories.CountryRepository;

/**
 * Service class This class contains all the functions to handle the data, is
 * the most important class. Also, will be called from controller class
 * 
 * @author JMHR
 *
 */

@Component // This annotation is used to inject this class in other. For this case This
			// class will be injected in CountryController
@Service // This annotation is used to specify that this is a service class that contains
			// all the methods
			// An the repository is who will be communicated with the database
public class CountryService {

	//// ################ Spring boot automatically return the objects in JSON
	//// format

	@Autowired // @Autowired -- is dependency injection
	// Internally this class is communicating with the repository.
	//
	CountryRepository countryrep;

	// This method returns the list of all the countries
	public List<Country> getAllCountries() {
		return countryrep.findAll(); // Will return all the data available in the table
	}

	// This method returns an specific information country
	public Country getCountrybyID(int id) {
		return countryrep.findById(id).get();
	}

	// This method returns the object according to the country name
	// CountryIdMap.keySet() means that the for loop is going to iterate on each row
	// and will compare the name of the country
	public Country getCountrybyName(String countryName) {
		List<Country> countries = countryrep.findAll();
		Country country = null;
		for (Country con : countries) {
			if (con.getCountryName().equalsIgnoreCase(countryName))
				country = con;
		}
		return country;
	}

	// This method is used to add a new country
	public Country addCountry(Country country) {
		country.setId(getMaxId());
		countryrep.save(country);
		return country;
	}

	// This utility function is used to generate the last id available in the
	// table to create a new object
	public int getMaxId() {
		return countryrep.findAll().size() + 1;
	}

	// The method is used to update a register in the hashMap
	public Country updateCountry(Country country) {
		countryrep.save(country);
		return country;
	}

	// This method is used to remove an specific row and return a message
	public AddResponse deleteCountry(int id) {
		countryrep.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country Deleted !!!");
		res.setId(id);
		return res;
	}

}
