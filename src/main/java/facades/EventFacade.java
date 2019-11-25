package facades;

import ch.hsr.geohash.GeoHash;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import org.apache.http.client.utils.URIBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import dto.CityDTO;
import dto.EventDTO;
import dto.LocationDateDTO;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Get data from the ticketmaster API.Calls the private methods
     * uriBuilder(), which builds the uri with the help of calculateRadius() and
     * then the callApi() is called with the uri and the answer from
     * ticketmaster is returned as a List of EventDTO's.
     *
     * @param locDate LocationDateDTO
     * @param city CityDTO
     * @return List of EventDTO's created from response from ticketmaster API
     * @throws errorhandling.NotFoundException
     */
    public List<EventDTO> getApiData(LocationDateDTO locDate, CityDTO city) throws NotFoundException {
        String uri = uriBuilder(Double.parseDouble(city.getLatitude()), Double.parseDouble(city.getLongitude()),
                locDate.getStartdate(), locDate.getEnddate(), calculateRadius(city.getPopulation()));
        JsonObject response = callApi(uri);

        JsonObject validResponse = response.getAsJsonObject("page");
        if (validResponse == null){
            throw new NotFoundException("Inputdata is not valid");
        }
        JsonObject embedded = response.getAsJsonObject("_embedded");
        if (embedded == null){
            throw new NotFoundException("No events for this City exists");
        }
        
        JsonArray array = embedded.getAsJsonArray("events");
        List<EventDTO> events = new ArrayList();
        
        for (int i = 0; i < array.size(); i++) {
            
            JsonObject event = array.get(i).getAsJsonObject();
            EventDTO eventdto = new EventDTO();
            eventdto.setEventName(event.get("name").getAsString());
            eventdto.setEventURL(event.get("url").getAsString());
            
            JsonObject dates = event.get("dates").getAsJsonObject().get("start").getAsJsonObject();
            eventdto.setEventDate(dates.get("localDate").getAsString());
            
            JsonObject embeddedVenues = event.getAsJsonObject("_embedded").getAsJsonArray("venues").get(0).getAsJsonObject();
            eventdto.setEventAddress(embeddedVenues.getAsJsonObject("address").get("line1").getAsString());
            eventdto.setLatitude(embeddedVenues.getAsJsonObject("location").get("latitude").getAsString());
            eventdto.setLongitude(embeddedVenues.getAsJsonObject("location").get("longitude").getAsString());
            events.add(eventdto);
        }
        return events;
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
     * ticketmaster API. The API prefers that locations are hashed to a geohash
     * or geopoint and to achieve this we use the library geohash-java
     * (https://github.com/kungfoo/geohash-java). 
     * To create the geohash, the latitude and longitude of a location is 
     * needed along with a value representing the number of characters in the 
     * geohash. The longer the hash the more precise the location. The ticketmaster
     * Api doesn't accpet geohashes longer than 9 characters. 
     *
     *
     * @param latitude
     * @param longitude
     * @param startdate
     * @param enddate
     * @param calcRadius
     * @return String representation of the build uri
     * @throws errorhandling.NotFoundException
     */
    private String uriBuilder(Double latitude, Double longitude, String startdate, String enddate, String calcRadius) throws NotFoundException {
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
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * The method that makes the call to the ticketmaster API using the uri build
     * in the uriBuilder(). 
     * 
     * @param uri
     * @return String representation of the response
     * @throws errorhandling.NotFoundException
     */
    private JsonObject callApi(String uri) throws NotFoundException {
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
                return GSON.fromJson(response, JsonObject.class);
            }
        } catch (JsonSyntaxException | IOException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
