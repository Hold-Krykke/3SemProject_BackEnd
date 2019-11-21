package facades;

import ch.hsr.geohash.GeoHash;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import org.apache.http.client.utils.URIBuilder;
import com.google.gson.Gson;
import dto.CityDTO;
import dto.LocationDateDTO;

/**
 *
 * @author Camilla
 */
public class EventFacade {

    private static EventFacade instance;
    private final String baseURL = "https://app.ticketmaster.com/discovery/v2/events.json?";
    private final Gson GSON = new Gson();

    private EventFacade() {
    }

    /**
     * @return an instance of the EventFacade class.
     */
    public static EventFacade getEventFacade() {
        if (instance == null) {
            instance = new EventFacade();
        }
        return instance;
    }

    /**
     * Get data from the ticketmaster API. Calls the private methods
     * uriBuilder(), which builds the uri with the help of calculateRadius() and
     * then the callApi() is called with the uri and the answer from
     * ticketmaster is returned as a String.
     *
     * @param locDate LocationDateDTO
     * @param city CityDTO
     * @return String response from ticketmaster API
     */
    public String getApiData(LocationDateDTO locDate, CityDTO city) {

        String uri = uriBuilder(Double.parseDouble(city.getLatitude()), Double.parseDouble(city.getLongitude()),
                locDate.getStartdate(), locDate.getEnddate(), calculateRadius(city.getPopulation()));
        return callApi(uri);
    }

    /**
     * Method to calculate the radius for the uriBuilder(), based on population.
     * This is needed since there's a big difference in the Area of the European
     * cities. The radius is calculated as the population divided by 100,000
     * (ceiled).
     *
     * @param population
     * @return String representation of the calculated radius
     */
    private String calculateRadius(String population) {
        String radius = "1";
        int pop = Integer.parseInt(population);
        if (pop > 100000) {
            int tempRadius = (int) Math.ceil(pop / 100000);
            radius = Integer.toString(tempRadius);
        }
        return radius;
    }

    /**
     * The uriBuilder uses the apache Class URIBuilder to create the uri for the
     * ticketmaster API. The API prefers that locations are hashed to a geohash,
     * and to achieve this we use the library geohash-java
     * (https://github.com/kungfoo/geohash-java).
     *
     *
     * @param latitude
     * @param longitude
     * @param startdate
     * @param enddate
     * @param calcRadius
     * @return String representation of the build uri
     */
    private String uriBuilder(Double latitude, Double longitude, String startdate, String enddate, String calcRadius) {
        try {
            String paramGeoHash = "geoPoint";
            String paramRadius = "radius";
            String paramRadiusVal = calcRadius;
            String paramUnit = "unit";
            String paramStart = "startDateTime";
            String paramEnd = "endDateTime";
            String key = "apikey";
            String apiKey = "PXLz8SSxwRDS9HUxwZ9LVAkQELNMbma8";

            String paramGeohashVal = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 9);

            URIBuilder uribuilder = new URIBuilder(baseURL);
            uribuilder.addParameter(paramGeoHash, paramGeohashVal);
            uribuilder.addParameter(paramRadius, paramRadiusVal);
            uribuilder.addParameter(paramUnit, "km");
            uribuilder.addParameter(paramStart, startdate);
            uribuilder.addParameter(paramEnd, enddate);
            uribuilder.addParameter(key, apiKey);
            return uribuilder.toString();

        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * The method that makes the call to the ticketmaster API using the uri build
     * in the uriBuilder(). 
     * 
     * @param uri
     * @return String representation of the response
     */
    private String callApi(String uri) {
        try {
            URL siteURL = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent", "Application");

            try (Scanner scan = new Scanner(connection.getInputStream())) {
                String response = "";
                while (scan.hasNext()) {
                    response += scan.nextLine();
                }
                return GSON.fromJson(response, JsonObject.class).toString();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
