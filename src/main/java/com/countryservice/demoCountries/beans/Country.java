package com.countryservice.demoCountries.beans;

public class Country {
	
	//POJO Class or Bean class
	
	int id;
	String countryName;
	String countryCapital;
	
	public Country(int id, String countryName, String countryCapital) {
		this.id = id;
		this.countryName = countryName;
		this.countryCapital = countryCapital;
	}
		
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCountryCapital(String countryCapital) {
		this.countryCapital = countryCapital;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public String getCountryCapital() {
		return countryCapital;
	}

	

}
