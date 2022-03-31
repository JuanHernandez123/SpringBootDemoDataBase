package com.countryservice.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//POJO Class or Bean class

@Entity //This annotation means that this class reflects a table in the db
@Table(name="country") // This is the name of the table
public class Country {
	
	@Id // This annotation should be present when the column is a primary key
	@Column(name="id")
	int id;
	
	@Column(name="country_name")
	String countryName;
	
	@Column(name="capital")
	String countryCapital;
	
	//This constructor is implemented to take the default values because where is implemented
	// the database, we are not to use the constructor with parameters 
	public Country() {
		
	}	
	
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
