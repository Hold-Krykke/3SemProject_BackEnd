
package facades;

import ch.hsr.geohash.GeoHash;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author Camilla
 */
public class EventFacade {
    private ExecutorService executor = Executors.newCachedThreadPool();
    private String url = "https://app.ticketmaster.com/discovery/v2/events.json?";
    private String[] ENDPOINTS = {"", "", "", "", ""};


//    public Map<String, String> locationAndDate() throws InterruptedException, ExecutionException, TimeoutException {
//        Map<String, String> result = new HashMap();
//        Queue<Future<Pair<String, String>>> queue = new ArrayBlockingQueue(ENDPOINTS.length);
//
//        for (String endpoint : ENDPOINTS) {
//            Future<Pair<String, String>> future = executor.submit(new Callable<Pair<String, String>>() {
//                @Override
//                public Pair<String, String> call() throws Exception {
//                    return new ImmutablePair(endpoint.substring(0, endpoint.length()-1), getApiData(url + endpoint));
//                }
//            });
//            queue.add(future);
//        }
//
//        while (!queue.isEmpty()) {
//            Future<Pair<String, String>> epPair = queue.poll();
//            if (epPair.isDone()) {
//                result.put(epPair.get().getKey(), epPair.get().getValue());
//            } else {
//                queue.add(epPair);
//            }
//        }
//        executor.shutdown();
//        return result;
//    }

    public String getApiData(Double latitude, Double longitude, String url, String startdate, String enddate) throws URISyntaxException {
        // lat         long
        // 55.681554, 12.591135
        String result = "";
        
        //String paramCountry = "countryCode";
        String paramGeoHash = "geoPoint";
        String paramRadius = "radius";
        String paramRadiusVal = "5";
        String paramUnit = "unit";
        String paramStart = "startDateTime";
        String paramEnd = "endDateTime";
        String key = "apikey";
        String apiKey = "PXLz8SSxwRDS9HUxwZ9LVAkQELNMbma8";
        
//        GeoHashPoint geo = new GeoHashPoint(latitude, longitude);
//        String geohash = GeoHashExtensions.encode(53.5526394, 10.0067103);

       // GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, 6);
       String paramGeohashVal = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 10);
       //String paramGeohashVal = Integer.toString(geoHash.getPoint().hashCode());
        
        URIBuilder uribuilder = new URIBuilder(url);
        uribuilder.addParameter(paramGeoHash, paramGeohashVal);
        uribuilder.addParameter(paramRadius, paramRadiusVal);
        uribuilder.addParameter(paramUnit, "km");
        uribuilder.addParameter(paramStart, startdate);
        uribuilder.addParameter(paramEnd, enddate);
        uribuilder.addParameter(key, apiKey);
        
        
        String uri = uribuilder.toString();
        System.out.println("URL = " + uri);
        
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
                result = gson.fromJson(response, JsonObject.class).toString();
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }
}
