package lab.dao.spring.jdbc;

import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SimpleCountryJdbcDao extends NamedParameterJdbcDaoSupport implements CountryJdbcDao {

//    @Override
    public void save(Country country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            LOAD_COUNTRIES_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, country.getName());
                    ps.setString(2, country.getCodeName());
                    return ps;
                },
                keyHolder);
//        country.setId(keyHolder.getKey().intValue());
    }

//    @Override
    public Stream<Country> getAllCountries() {
        return getJdbcTemplate().query(
                GET_ALL_COUNTRIES_SQL,
                COUNTRY_ROW_MAPPER
        ).stream();
    }

    @Override
    public List<Country> getCountryListStartWith(String name) {
        return getNamedParameterJdbcTemplate()
                .query(
                        GET_COUNTRIES_BY_NAME_SQL,
                        new MapSqlParameterSource("name", name + "%"),
                        COUNTRY_ROW_MAPPER);
    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        getJdbcTemplate().update(
                        UPDATE_COUNTRY_NAME_SQL,
                        newCountryName,
                        codeName
        );
    }

    @Override
    public Country getCountryByCodeName(String codeName) {
        return getJdbcTemplate()
                .queryForObject(GET_COUNTRY_BY_CODE_NAME_SQL, COUNTRY_ROW_MAPPER, codeName);
    }

//    @Override
    public Optional<Country> getCountryByName(String name) {
        try {
            return Optional.of(
                    getJdbcTemplate()
                    .queryForObject(GET_COUNTRY_BY_NAME_SQL, COUNTRY_ROW_MAPPER, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void loadCountries() {
        for (String[] strings : COUNTRY_INIT_DATA)
            save(new SimpleCountry(strings[0], strings[1]));
    }
}
