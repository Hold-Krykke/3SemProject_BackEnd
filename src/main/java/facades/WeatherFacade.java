package facades;

import dto.WeatherDTO;

public class WeatherFacade {

    private static WeatherFacade instance;

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

    public String getFacadeMessage() {
        return "Hello World";
    }

    public WeatherDTO getWeather(String city, String year, String month, String day) {
        
    }
}
