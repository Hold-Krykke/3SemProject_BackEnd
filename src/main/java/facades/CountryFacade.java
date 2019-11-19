package facades;

import dto.CountryDTO;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CountryFacade {

    private static CountryFacade instance;
    private List<CountryDTO> countries = new ArrayList<>();

    //Private Constructor to ensure Singleton
    private CountryFacade() {
    }

    /**
     *
     *
     * @return an instance of this facade class.
     */
    public static CountryFacade getCountryFacade() {
        if (instance == null) {
            instance = new CountryFacade();
        }
        return instance;
    }

    //TODO Remove/Change this before use
    public String getFacadeMessage() {
        return "Hello from the facade";
    }

    public List<CountryDTO> getCountries() {
        return this.countries;
    }

    public List<CountryDTO> addCountry(CountryDTO country) {
        this.countries.add(country);
        return this.countries;
    }

    /**
     * Get Country by Name. 
     * @param name of the country.
     * @return CountryDTO
     * @throws NotFoundException 
     */
    public CountryDTO getCountry(String name) throws NotFoundException {
        CountryDTO country = null;
        for (CountryDTO c : this.countries) {
            if (c.getName().equals(name)) {
                country = c;
            }
        }

        if (country == null) {
            throw new NotFoundException("No country by that name exists.");
        }

        return country;

    }

}
