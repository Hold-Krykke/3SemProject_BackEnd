package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.WeatherDTO;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherFacade {

    private static WeatherFacade instance;

    private final String baseUrl = "https://ajuhlhansen.dk/WeatherCloud/api/weather/city/";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final String oldDateMsg = "I dont think we use the same calendar";

    //Private Constructor to ensure Singleton
    private WeatherFacade() {
    }

    /**
     * @return an instance of this facade class.
     */
    public static WeatherFacade getWeatherFacade() {
        if (instance == null) {
            instance = new WeatherFacade();
        }

        return instance;
    }

    private List<WeatherDTO> getData(String urlFragment) throws NotFoundException {
        String uri = baseUrl + urlFragment;
        List<WeatherDTO> weatherReports = new ArrayList<>();
        String errorMessage = "";
        try {
            URL url = new URL(uri);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                        errorMessage = GSON.fromJson(response, JsonObject.class).toString();
                    } else if (jsonElement.isJsonArray()) {
                        JsonArray array = GSON.fromJson(response, JsonArray.class);
                        array.forEach(object -> {
                            weatherReports.add(GSON.fromJson(object, WeatherDTO.class));
                        });
                    }

                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new NotFoundException(errorMessage);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            throw new NotFoundException(errorMessage);
        }
        return weatherReports;
    }

    public String getFacadeMessage() {
        return "Hello World";
    }

    public List<WeatherDTO> getWeather(String city, String year, String month, String day) throws NotFoundException {
        String url = "" + city + "/" + year + "/" + month + "/" + day;
        return getData(url);
    }

    public List<WeatherDTO> get5Days(String city) throws NotFoundException {
        return getData(city);
    }

}
