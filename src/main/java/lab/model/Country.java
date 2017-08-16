package lab.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface Country {
    String getName();
    String getCodeName();

    static void save(Country country) {
        // TODO: 01/08/2017 realize it
    }

    static Stream<Country> getAllCountries() {
        // TODO: 01/08/2017 realize it
        return null;
    }

    static Optional<Country> getCountryByName(String name) {
        // TODO: 01/08/2017 realize it
        return null;
    }
}
