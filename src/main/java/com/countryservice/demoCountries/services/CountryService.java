package com.countryservice.demoCountries.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.countryservice.demoCountries.beans.Country;
import com.countryservice.demoCountries.controllers.AddResponse;

/**
 * This class contains all the functions to handle the data, is the most important class. Also, will be called from controller class
 * @author JMHR
 *
 */

@Component // This annotation is used to inject this class in other. For this case This class will be injected in CountryController
public class CountryService {
	
	////################ Spring boot automatically return the objects in JSON format

	static HashMap<Integer, Country> countryIdMap;

	// Constructor to initialize the values
	// Country (1,"India","Delhi"); Is the constructor for the class

	public CountryService() {
		countryIdMap = new HashMap<Integer, Country>();
		Country indiaCountry = new Country(1, "India", "Delhi");
		Country usaCountry = new Country(2, "USA", "Washington");
		Country ukCountry = new Country(3, "UK", "London");

		countryIdMap.put(1, indiaCountry);
		countryIdMap.put(2, usaCountry);
		countryIdMap.put(3, ukCountry);

	}

	// This method returns the list of all the countries
	public List getAllCountries() {
		List countries = new ArrayList(countryIdMap.values());
		return countries;
	}

	// This method returns an specific information country
	public Country getCountrybyID(int id) {
		Country country = countryIdMap.get(id);
		return country;
	}

	// This method returns the object according to the country name
	// CountryIdMap.keySet() means that the for loop is going to iterate on each row
	// and will compare the name of the country
	public Country getCountrybyName(String countryName) {
		Country country = null;
		for (int i : countryIdMap.keySet()) {
			if (countryIdMap.get(i).getCountryName().equals(countryName)) {
				country = countryIdMap.get(i);
			}
		}
		return country;
	}

	// This method is used to add a new country
	public Country addCountry(Country country) {
		country.setId(getMaxId());
		countryIdMap.put(country.getId(), country);
		return country;
	}

	// This utility function is used to generate the last id available in the
	// HashMap to create a new object
	public static int getMaxId() {
		int max = 0;
		for (int id : countryIdMap.keySet()) {
			if (max <= id) {
				max = id;
			}
		}
		return max + 1;
	}

	//The method is used to update a register in the hashMap
	public Country updateCountry(Country country) {
		//First to all validate if there are information in the HashMap
		if (country.getId() > 0) {
			countryIdMap.put(country.getId(), country);
		}
		return country;
	}
	
	// This method is used to remove an specific row and return a message
	public AddResponse deleteCountry(int id) {
		countryIdMap.remove(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country deleted..");
		res.setId(id);
		return res;
	}
}
