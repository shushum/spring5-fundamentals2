package lab.dao;

import lab.model.Country;

import java.util.List;

public interface CountryDao {

    List<Country> getCountryListStartWith(String name);

    void updateCountryName(String codeName, String newCountryName);

    void loadCountries();

    Country getCountryByCodeName(String codeName);
}
