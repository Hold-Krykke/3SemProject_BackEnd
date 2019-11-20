package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.CountryFacade;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * As a start, this class is only meant for handling multiple endpoint calls
 * using threads.
 *
 * If we in the future find need for other utils, they can be added here or in
 * this package.
 *
 * If this class is improved upon with new features, consider moving the
 * ExecutorService out of getApiData()
 *
 */
public class APIUtil {

    private Gson GSON = new Gson();

    /**
     * Feed this method with a list of endpoints and it will return a collection
     * of the data.Would have been useful to know Generics to code this.
     *
     * @param endpoints a list of URLS you wish to access. (full URLs, example:
     * https://www.api.example.com/example/exampledata))
     *
     * How to use the returned data:
     *
     * Convert the string into a JsonArray (Gson) and run through every element
     * in that array. Use JsonObject/JsonElement (Gson) and check every item
     * with get/getProperty. For an example, see getCountriesAndCities()
     * @see CountryFacade#getCountriesAndCities()
     *
     * For an example of usage, see its test in {Test
     * Packages\\utils\\APIUtilTest.java}
     *
     * @return returns a list of the data at the given endpoints.
     * @see APIUtil#getData(java.lang.String)
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public List<String> getApiData(ArrayList<String> endpoints) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<String> result = new ArrayList();
        Queue<Future<String>> queue = new ArrayBlockingQueue(endpoints.size());

        for (String endpoint : endpoints) {
            Future<String> future = executor.submit(() -> getData(endpoint));
            queue.add(future);
        }

        while (!queue.isEmpty()) {
            Future<String> data = queue.poll();
            if (data.isDone()) {
                result.add(data.get());
            } else {
                queue.add(data);
            }
        }
        executor.shutdown();
        return result;
    }

    /**
     * Returns a string of the data at the endpoint in JSON-format. Handles both
     * objects {} and arrays [{}]
     *
     * @param url endpoint in question (full url - that meaning
     * https://www.api.example.com/example/exampledata)
     *
     * Would be nice to be able to dynamically set headers.
     * @return
     */
    private String getData(String url) {
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
                return response;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
