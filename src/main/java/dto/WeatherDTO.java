/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Malte
 */
public class WeatherDTO {
    
    /*
    {
    "dateTime":"10.10.2016 10:20",
    "funnyAdvice":"An umbrella is not going to be enough, take a cap or drown in the rain",
    "humidity":91,
    "predictability":77,
    "temp":10.375,
    "weatherIcon":"https://www.metaweather.com/static/img/weather/png/hr.png",
    "weatherStatus":"Heavy Rain",
    "windDirection":"NE",
    "windSpeed":13.072619799015087}
    */
    
    private String dateTime;
    private String funnyAdvice;
    private String weatherIcon;
    private String weatherStatus;
    private String windDirection;
    private int humidity;
    private int predictability;
    private double temp;
    private double windSpeed;

    public WeatherDTO() {
    }
    
    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return the funnyAdvice
     */
    public String getFunnyAdvice() {
        return funnyAdvice;
    }

    /**
     * @param funnyAdvice the funnyAdvice to set
     */
    public void setFunnyAdvice(String funnyAdvice) {
        this.funnyAdvice = funnyAdvice;
    }

    /**
     * @return the weatherIcon
     */
    public String getWeatherIcon() {
        return weatherIcon;
    }

    /**
     * @param weatherIcon the weatherIcon to set
     */
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    /**
     * @return the weatherStatus
     */
    public String getWeatherStatus() {
        return weatherStatus;
    }

    /**
     * @param weatherStatus the weatherStatus to set
     */
    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    /**
     * @return the windDirection
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * @return the humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * @param humidity the humidity to set
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * @return the predictability
     */
    public int getPredictability() {
        return predictability;
    }

    /**
     * @param predictability the predictability to set
     */
    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    /**
     * @return the temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * @return the windSpeed
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    

}
