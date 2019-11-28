/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Objects;

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
     * Make a new WeatherDTO.
     *
     * @param dateTime
     * @param funnyAdvice
     * @param weatherIcon
     * @param weatherStatus
     * @param windDirection
     * @param humidity
     * @param predictability
     * @param temp
     * @param windSpeed
     */
    public WeatherDTO(String dateTime, String funnyAdvice, String weatherIcon, String weatherStatus, String windDirection, int humidity, int predictability, double temp, double windSpeed) {
        this.dateTime = dateTime;
        this.funnyAdvice = funnyAdvice;
        this.weatherIcon = weatherIcon;
        this.weatherStatus = weatherStatus;
        this.windDirection = windDirection;
        this.humidity = humidity;
        this.predictability = predictability;
        this.temp = temp;
        this.windSpeed = windSpeed;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.dateTime);
        hash = 43 * hash + Objects.hashCode(this.funnyAdvice);
        hash = 43 * hash + Objects.hashCode(this.weatherIcon);
        hash = 43 * hash + Objects.hashCode(this.weatherStatus);
        hash = 43 * hash + Objects.hashCode(this.windDirection);
        hash = 43 * hash + this.humidity;
        hash = 43 * hash + this.predictability;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.temp) ^ (Double.doubleToLongBits(this.temp) >>> 32));
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.windSpeed) ^ (Double.doubleToLongBits(this.windSpeed) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WeatherDTO other = (WeatherDTO) obj;
        if (this.humidity != other.humidity) {
            return false;
        }
        if (this.predictability != other.predictability) {
            return false;
        }
        if (Double.doubleToLongBits(this.temp) != Double.doubleToLongBits(other.temp)) {
            return false;
        }
        if (Double.doubleToLongBits(this.windSpeed) != Double.doubleToLongBits(other.windSpeed)) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        if (!Objects.equals(this.funnyAdvice, other.funnyAdvice)) {
            return false;
        }
        if (!Objects.equals(this.weatherIcon, other.weatherIcon)) {
            return false;
        }
        if (!Objects.equals(this.weatherStatus, other.weatherStatus)) {
            return false;
        }
        if (!Objects.equals(this.windDirection, other.windDirection)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WeatherDTO{" + "dateTime=" + dateTime + ", funnyAdvice=" + funnyAdvice + ", weatherIcon=" + weatherIcon + ", weatherStatus=" + weatherStatus + ", windDirection=" + windDirection + ", humidity=" + humidity + ", predictability=" + predictability + ", temp=" + temp + ", windSpeed=" + windSpeed + '}';
    }

}
