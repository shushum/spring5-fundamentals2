package orm;

import lab.dao.CountryDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:orm.xml")
class CountryDaoImplTest {

    private Country exampleCountry =
            new SimpleCountry(1, "Australia", "AU");

    @Autowired
    @Qualifier("countryJpaDaoImpl")
    private CountryDao countryDao;

    @Test
    @DirtiesContext
    void testSaveCountry2() {
        countryDao.save(exampleCountry);
        assertThat(Optional.of(exampleCountry),
                is(countryDao.getAllCountries().findFirst()));
        countryDao.remove(exampleCountry);
    }

    @Test
    @DirtiesContext
    void testGetAllCountries() {
        SimpleCountry country = new SimpleCountry(1, "Canada", "CA");
        countryDao.save(country);
        assertEquals(1, countryDao.getAllCountries().count());
        countryDao.remove(country);
    }

    @Test
    @DirtiesContext
    void testGetCountryByName() {
        countryDao.save(exampleCountry);
        assertEquals(exampleCountry,
                countryDao.getCountryByName("Australia")
                        .orElseThrow(RuntimeException::new));
        countryDao.remove(exampleCountry);
    }

}
