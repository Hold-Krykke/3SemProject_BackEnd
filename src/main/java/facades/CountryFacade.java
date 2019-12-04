package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.CityDTO;
import dto.CountryDTO;
import errorhandling.APIUtilException;
import errorhandling.NotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.APIUtil;

public class CountryFacade {

    private static CountryFacade instance;
    private static List<CountryDTO> countries;

    private static final String RESTCOUNTRIESURL = "https://restcountries.eu/rest/v2/";
    private static final String GEONAMESURL = "http://api.geonames.org/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Private Constructor to ensure Singleton
    private CountryFacade() {
    }

    /**
     *
     *
     * @return an instance of this facade class.
     */
    public static CountryFacade getCountryFacade() throws APIUtilException {
        if (instance == null) {
            instance = new CountryFacade();
        }

        if (countries == null || countries.isEmpty()) {
            countries = new ArrayList();
            instance.getEuropeanCountriesAndCities();
        }
        System.out.println("FACADE.COUNTRIES:\n" + GSON.toJson(countries)); //Use if you want to see data
        return instance;
    }

    public String getFacadeMessage() {
        return "Hello from the facade";
    }

    public List<CountryDTO> getCountries() {
        return this.countries;
    }

    public List<CountryDTO> addCountry(CountryDTO country) {
        countries.add(country);
        return countries;
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
            if (c.getCountryName().equals(name)) {
                country = c;
            }
        }
        if (country == null) {
            throw new NotFoundException("No country by that name exists");
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
        JsonObject country = GSON.fromJson(data, JsonObject.class);
        if (country == null || country.get("name") == null) {
            throw new NotFoundException("No country with given alpha2 code found");
        }
        return country.get("name").getAsString();
    }

    /**
     * Helper method that fetches data from the restcountries API.
     *
     * @param uriPart
     * @return JSON fetched data
     */
    private String getRestcountriesData(String uriPart) {
        String url = RESTCOUNTRIESURL + uriPart;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent", "Application");
            try (Scanner scan = new Scanner(connection.getInputStream(), "UTF-8")) {
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
     * Using two helper methods we get european countries and their 5 largest
     * cities.
     *
     * This information - countryName and a list of cities - is set on the
     * facades arraylist 'countries'
     *
     * Follows CountryDTO format of {countryName:[city1,city2,city3...]}
     *
     * @see CountryFacade#getEuropeanCountryCodes()
     * @see CountryFacade#getEuropeanCityData(java.util.Map)
     */
    private void getEuropeanCountriesAndCities() throws APIUtilException {
        //We are only here if we are on a new backend or an error happened. Countries is empty.

        /*Getting Alpha2Codes in europe */
        List<String> euCountryCodes = getEuropeanCountryCodes();

        /* Getting and setting 5 largest cities in european countries*/
        getEuropeanCityData(euCountryCodes);
    }

    /**
     * Takes use of getRestcountriesData() and gets a list of ISO 3166-1
     * 2-letter country codes. (dk, se, no, gb, de, fr, es, etc)
     *
     * @see CountryFacade#getRestcountriesData(java.lang.String)
     * @see CountryFacade#RESTCOUNTRIESURL
     * @see CountryFacade#getEuropeanCityData(java.util.Map)
     * @return returns a list containing "Alpha2Code, e.g. "dk"
     */
    private List<String> getEuropeanCountryCodes() {
        //Get and convert data
        JsonArray countryCodes = GSON.fromJson(getRestcountriesData("region/europe?fields=alpha2Code"), JsonArray.class);

        //instantiate and populate list of countrycodes
        List<String> euCountriesData = new ArrayList();
        for (JsonElement jsonString : countryCodes) {
            JsonObject obj = jsonString.getAsJsonObject();
            euCountriesData.add(obj.get("alpha2Code").toString().toLowerCase()); //returns "DK" but we want "dk"
        }
        return euCountriesData;
    }

    /**
     *
     * Populates the global 'countries' list with countries, cities using our
     * list of Country Codes.
     *
     * @see CountryFacade#GEONAMESURL
     * @see CountryFacade#getEuropeanCountryCodes()
     *
     * @param countryCodes A list containing "Alpha2Code", e.g."dk"
     */
    private void getEuropeanCityData(List<String> countryCodes) throws APIUtilException {
        /*Instantiate*/
        APIUtil utils = new APIUtil();
        String url = GEONAMESURL;
        url += "search?"; //Accesing API textsearch 
        url += "username=holdkrykke"; //Required to track our usage. (Reminds you of an API key)
        url += "&type=json"; //We want JSON returned. Also possible: XML, rdf
        url += "&maxRows=10"; //Maximum 10 results. (We need 5 but will sort through these)
        url += "&featureClass=P"; //featureClass P is city, village, populated area
        url += "&orderby=population"; //Get results ordered by population (hi-lo)
        url += "&country="; //Alpha2Code of the country in question. To be set later.
        //url = http://api.geonames.org/search?username=holdkrykke&type=json&maxRows=10&featureClass=P&orderby=population&country=

        /* Create list of endpoints required for utils */
        ArrayList<String> endpoints = new ArrayList();
        for (String countrycode : countryCodes) {
            String innerURL = url;
            innerURL += countrycode;
            endpoints.add(innerURL);
        }

        /* Get data from all endpoints */
        List<String> data = utils.getApiData(endpoints);

        /* Traverse returned data */
        for (String string : data) { //This is each country
            CountryDTO country = new CountryDTO();
            List<String> knownRegions = new ArrayList(); //holds already registered regions. 

            //Convert returned JsonString to JsonObj
            JsonObject dataString = GSON.fromJson(string, JsonObject.class);
            JsonArray array = dataString.getAsJsonArray("geonames"); //Get the only info we want -- >citydata< -- This is a list of all cities and their info.

            for (int i = 0; i < array.size(); i++) { //This is each city
                JsonObject city = array.get(i).getAsJsonObject();

                /*On first iteration, set countryName, countryCode.*/
                if (i == 0) {
                    country.setCountryName(city.get("countryName").getAsString());
                    country.setCountryCode(city.get("countryCode").getAsString().toLowerCase());
                }

                /* We run through a list of 10 cities and want maximum 5 */
                if (country.getCities().size() >= 5) {
                    continue;
                }

                //Check if we have already seen this region
                //(because data is imperfect, we want to spread cities over different parts of the country)
                String cityRegion = city.get("adminName1").getAsString();
                if (knownRegions.contains(cityRegion)) {
                    continue;
                } else {
                    knownRegions.add(cityRegion);
                }

                /* Get and set data */
                String cityName = city.get("toponymName").getAsString();
                String population = city.get("population").getAsString();
                String latitude = city.get("lat").getAsString();
                String longitude = city.get("lng").getAsString();
                country.addCity(new CityDTO(cityName, population, latitude, longitude));
            }
            addCountry(country);
        }
    }

}
