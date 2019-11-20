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

public class APIUtil {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private static final Gson GSON = new Gson();

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
     * @return returns a list of the data at the given endpoints.
     * @see APIUtil#getData(java.lang.String)
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public List<String> getApiData(ArrayList<String> endpoints) throws InterruptedException, ExecutionException, TimeoutException {
        List<String> result = new ArrayList();
        Queue<Future<String>> queue = new ArrayBlockingQueue(endpoints.size());

        for (String endpoint : endpoints) {
            Future<String> future = EXECUTOR.submit(() -> getData(endpoint));
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
        EXECUTOR.shutdown();
        return result;
    }

    /**
     * Returns a string of the data at the endpoint in JSON-format. Handles both
     * objects {} and arrays [{}]
     *
     * @param url endpoint in question (full url - that meaning
     * https://www.api.example.com/example/exampledata)
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
            }
        } catch (Exception e) {
        }
        return "";
    }
}
