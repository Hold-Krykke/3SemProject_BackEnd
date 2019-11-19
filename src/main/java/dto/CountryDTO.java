/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Malte
 */
public class CountryDTO {

    private List<String> cities = new ArrayList<>();

    public CountryDTO() {
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public void addCity(String city) {
        this.cities.add(city);
    }
}
