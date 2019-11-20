
package facades;

import ch.hsr.geohash.GeoHash;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.client.utils.URIBuilder;
import com.google.gson.Gson;
import dto.CountryDTO;
import java.util.ArrayList;
import java.util.List;
// import com.google.gson.Gson;
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
    
    public static EventFacade getEventFacade() {
        if (instance == null) {
            instance = new EventFacade();
        }
        return instance;
    }
    
    
    public String getApiData(Double latitude, Double longitude, String startdate, String enddate) throws URISyntaxException {
        String paramGeoHash = "geoPoint";
        String paramRadius = "radius";
        String paramRadiusVal = "5";
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
        
        String uri = uribuilder.toString();
        System.out.println("URL: " + uri);
        try {
            URL siteURL = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent", "Application");
            
            try (Scanner scan = new Scanner(connection.getInputStream())) {
                String response = "";
                while(scan.hasNext()) {
                    response += scan.nextLine();
                }
                Gson gson = new Gson();               
                return gson.fromJson(response, JsonObject.class).toString();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
