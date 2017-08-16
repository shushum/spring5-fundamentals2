package lab.dao.spring.jdbc;

import lab.dao.CountryDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface CountryJdbcDao extends CountryDao, InitializingBean {
    String LOAD_COUNTRIES_SQL = "INSERT INTO country (name, code_name) VALUES (?, ?)";
    String GET_ALL_COUNTRIES_SQL = "SELECT id, name, code_name FROM country";
    String GET_COUNTRIES_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name LIKE :name";
    String GET_COUNTRY_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name = ?";
    String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT id, name, code_name FROM country WHERE code_name = ?";
    String UPDATE_COUNTRY_NAME_SQL = "UPDATE country SET name=? WHERE code_name=?";

    String[][] COUNTRY_INIT_DATA = {
            {"Australia", "AU"}, //0
            {"Canada", "CA"}, //1
            {"France", "FR"}, //2
            {"Hong Kong", "HK"}, //3
            {"Iceland", "IC"}, //4
            {"Japan", "JP"},//5
            {"Nepal", "NP"},//6
            {"Russian Federation", "RU"},//7
            {"Sweden", "SE"},//8
            {"Switzerland", "CH"},//9
            {"United Kingdom", "GB"},//10
            {"United States", "US"}//11
    };

    RowMapper<Country> COUNTRY_ROW_MAPPER = (rs, rowNum) ->
            new SimpleCountry(
//                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("code_name"));

    List<Country> getCountryListStartWith(String name);

    void updateCountryName(String codeName, String newCountryName);

    void loadCountries();

    Country getCountryByCodeName(String codeName);
}
