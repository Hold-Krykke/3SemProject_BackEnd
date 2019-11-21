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
    String endtime = "T23:59:59Z";
    String starttime = "T00:00:00Z";
    
    /**
     * Empty constructor
     */
    public LocationDateDTO() {
    }

    /**
     * Constructor
     * @param startdate
     * @param enddate
     * @param country
     * @param city
     */
    public LocationDateDTO(String startdate, String enddate, String country, String city) {
        this.startdate = startdate + starttime;
        this.enddate = enddate + endtime;
        this.country = country;
        this.city = city;
    }

    /**
     * Get startdate in format "2019-11-23T00:00:00Z"
     * @return
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * Set startdate in format "YYYY-MM-DD" and only in this format. 
     * @param startdate
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate + starttime;
    }

    /**
     * Get enddate in format "2019-11-23T00:00:00Z"
     * @return
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * Set enddate in format "YYYY-MM-DD" and only in this format.
     * @param enddate
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate + endtime;
    }

    /**
     * Get Country
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set Country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get city
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "LocationDateDTO{" + "startdate=" + startdate + ", enddate=" + enddate + ", country=" + country + ", city=" + city + ", endtime=" + endtime + ", starttime=" + starttime + '}';
    }
    
    
}
