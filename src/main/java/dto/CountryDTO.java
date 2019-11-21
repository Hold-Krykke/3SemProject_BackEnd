package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryDTO {

    private String countryName, countryCode;
    private List<CityDTO> cities = new ArrayList<>();

    /**
     *
     * Constructor for copy/clone
     *
     * This is called 'deep cloning'. It removes all code references for a 'true
     * clone'.
     *
     * In particular this is for cloning without implementing Object.clone()
     * (which is poorly written and flawed)
     *
     * For more information::
     *
     * https://www.baeldung.com/java-deep-copy#1-copy-constructor
     *
     * https://stackoverflow.com/a/715901
     *
     * @param dto dto to clone
     */
    public CountryDTO(CountryDTO dto) {
        this.countryName = dto.getCountryName();
        this.countryCode = dto.getCountryCode();
        this.cities = new ArrayList(dto.getCities().size());
        for (CityDTO city : dto.getCities()) {
            this.cities.add(new CityDTO(city));
        }
    }

    public CountryDTO(String name) {
        this.countryName = name;
    }

    public CountryDTO(String name, List<CityDTO> cities) {
        this.countryName = name;
        this.cities = cities;
    }

    public CountryDTO(String name, String countryCode) {
        this.countryName = name;
        this.countryCode = countryCode;
    }

    public CountryDTO(String name, String countryCode, List<CityDTO> cities) {
        this.countryName = name;
        this.countryCode = countryCode;
        this.cities = cities;
    }

    public CountryDTO() {
    }

    /**
     * Gets a specific city from the countries list of cities
     *
     * @param name name of specific city.
     * @return
     */
    public CityDTO getSpecificCityByName(String name) {
        for (CityDTO city : this.cities) {
            if (city.getCityName().equals(name)) {
                return city;
            }
        }
        return null;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }

    public void addCity(CityDTO city) {
        this.cities.add(city);
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
        final CountryDTO other = (CountryDTO) obj;
        if (!Objects.equals(this.countryName, other.countryName)) {
            return false;
        }
        if (!Objects.equals(this.countryCode, other.countryCode)) {
            return false;
        }
        if (!Objects.equals(this.cities, other.cities)) {
            return false;
        }
        return true;
    }
  
    @Override
    public String toString() {
        return "CountryDTO{" + "countryName=" + countryName + ", countryCode=" + countryCode + ", cities=" + cities + '}';
    }

}
