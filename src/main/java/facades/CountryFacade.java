package facades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountryFacade {

    private static CountryFacade instance;
    private static List<CountryDTO> countries;

    private final String restcountriesURL = "https://restcountries.eu/rest/v2/";
    private final Gson GSON = new Gson();

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
        if (countries == null || countries.isEmpty()) {
            getCountriesAndCities();
        }
        return instance;
    }

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
     *
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

    /**
     * Used to get the full name of a country.
     *
     * @param alpha2 the alpha2 code for the country you want the name of
     * @return Name of country
     */
    public String getCountryNameByAlpha2(String alpha2) throws NotFoundException {
        String data = getRestcountriesData("alpha/" + alpha2);
        CountryDTO country = GSON.fromJson(data, CountryDTO.class);
        String name = country.getName();
        if (name == null || name.isEmpty()) {
            throw new NotFoundException("No country with given alpha2 code found");
        }
        return name;
    }

    /**
     * Helper method that fetches data from the restcountries API.
     *
     * @param uriPart
     * @return JSON fetched data
     */
    private String getRestcountriesData(String uriPart) {
        String url = restcountriesURL + uriPart;
        String result = "";
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent", "Application");
            try (Scanner scan = new Scanner(connection.getInputStream())) {
                String response = "";
                while (scan.hasNext()) {
                    response += scan.nextLine();
                }
                result = GSON.fromJson(response, JsonObject.class).toString();
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    private void getCountriesAndCities() {
        //We are only here if the list is null or empty, e.g. on a new backend
        countries = new ArrayList<>(); //init
        
    }
}
