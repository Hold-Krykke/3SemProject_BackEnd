package dto;

import java.util.Objects;

public class CityDTO {

    private String cityName, population, latitude, longitude;

    /**
     * Get the value of population
     *
     * @return the value of population
     */
    public String getPopulation() {
        return population;
    }

    /**
     * Set the value of population
     *
     * @param population new value of population
     */
    public void setPopulation(String population) {
        this.population = population;
    }

    /**
     * Constructor for copy/clone
     *
     * @param dto dto to clone
     */
    public CityDTO(CityDTO dto) {
        this.cityName = dto.getCityName();
        this.population = dto.getPopulation();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
    }

    public CityDTO(String cityName) {
        this.cityName = cityName;
    }

    public CityDTO() {
    }

    public CityDTO(String cityName, String population, String latitude, String longitude) {
        this.cityName = cityName;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Get the value of longtitude
     *
     * @return the value of longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Set the value of longitude
     *
     * @param longitude new value of longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the value of latitude
     *
     * @return the value of latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Set the value of latitude
     *
     * @param latitude new value of latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the value of cityName
     *
     * @return the value of cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Set the value of cityName
     *
     * @param cityName new value of cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        final CityDTO other = (CityDTO) obj;
        if (!Objects.equals(this.cityName, other.cityName)) {
            return false;
        }
        if (!Objects.equals(this.population, other.population)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CityDTO{" + "cityName=" + cityName + ", population=" + population + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}
