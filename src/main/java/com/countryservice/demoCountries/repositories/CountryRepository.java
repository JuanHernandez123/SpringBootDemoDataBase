package com.countryservice.demoCountries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countryservice.demoCountries.beans.Country;

//JpaRepository is the tool that will help us to communicate with the database
//Country parameter is the Bean class
//Integer is the type of the primary key, in this case id
public interface CountryRepository extends JpaRepository<Country,Integer> {

}
