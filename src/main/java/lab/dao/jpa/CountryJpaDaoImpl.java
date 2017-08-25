package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(country);
        transaction.commit();
        em.close();
    }

    @Override
    public Stream<Country> getAllCountries() {
        EntityManager em = emf.createEntityManager();
        Stream<Country> resultCountry = em.createQuery("select c from country c", Country.class)
                .getResultList()
                .stream();
        em.close();
        return resultCountry;
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        EntityManager em = emf.createEntityManager();
        Optional<Country> country = Optional.of(em
                .createQuery("SELECT c FROM country c WHERE c.name LIKE :name",
                        Country.class).setParameter("name", name)
                .getSingleResult());

        em.close();
        return country;
    }

    @Override
    public void remove(Country exampleCountry) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(exampleCountry));
        transaction.commit();
        em.close();
    }
}
