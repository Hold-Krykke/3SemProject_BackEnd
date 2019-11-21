package dto;

/**
 *
 * @author Camilla
 */
public class LocationDateDTO {

    String startdate;
    String enddate;
    String country;
    String city;
    

    public LocationDateDTO() {
    }

    public LocationDateDTO(String startdate, String enddate, String country, String city) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.country = country;
        this.city = city;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
