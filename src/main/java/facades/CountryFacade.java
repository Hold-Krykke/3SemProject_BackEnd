package facades;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CountryFacade {

    private static CountryFacade instance;
    private static List<CountryDTO> countries;

    private final String restcountriesURL = "https://restcountries.eu/rest/v2/";
    private final String geonamesURL = "http://api.geonames.org/";
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
            instance.getCountriesAndCities();
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
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(response);
                if (jsonElement.isJsonObject()) {
                    return GSON.fromJson(response, JsonObject.class).toString();
                } else if (jsonElement.isJsonArray()) {
                    return GSON.fromJson(response, JsonArray.class).toString();
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Sets the facades 'countries' arraylist to hold its DTO's
     *
     * Takes use of getRestcountriesData() and gets a list of european countries
     * and their ISO 3166-1 2-letter country code. (dk, se, no, gb, de, fr, es,
     * etc)
     *
     * Following that it grabs information about cities, sorted by country and
     * population.
     *
     * This information - countryName and a list of cities - is set on the
     * facades arraylist 'countries'
     *
     * Follows CountryDTO format of {countryName:city1,city2,city3...}
     */
    private void getCountriesAndCities() {
        //We are only here if the list is null or empty, e.g. on a new backend or an error happened
        System.out.println(countries);
        countries = new ArrayList<>(); //init
        //List<String> countryCodes = new ArrayList();
        //http://api.geonames.org/search?username=holdkrykke&country=dk&orderby=population&type=json&featureClass=P&maxRows=10

        String data = getRestcountriesData("region/europe?fields=name;alpha2Code");
        JsonArray country = GSON.fromJson(data, JsonArray.class);
        for (int i = 0; i < country.size(); i++) {
            JsonObject obj = country.get(i).getAsJsonObject();
            String countryName = obj.get("name").toString();
            String countryCode = obj.get("alpha2Code").toString();
            System.out.println("name: " + countryName + " countryCode: " + countryCode + "\n");
        }

        System.out.println("euCountriesData:\n" + data);
       
        //brug addCountry
        //getCities(countries); //how to handle exactly?
    }
}
