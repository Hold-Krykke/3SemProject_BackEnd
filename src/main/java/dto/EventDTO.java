
package dto;

/**
 *
 * @author Camilla
 */
public class EventDTO {
    private String eventName; 
    private String eventDate;
    private String eventAddress;
    private String eventURL;
    private String latitude;
    private String longitude;

    public EventDTO() {
    }

    public EventDTO(String eventName, String eventDate, String eventAddress, String eventURL, String latitude, String longitude) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventAddress = eventAddress;
        this.eventURL = eventURL;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventURL() {
        return eventURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "EventDTO{" + "eventName=" + eventName + ", eventDate=" + eventDate + ", eventAddress=" + eventAddress + ", eventURL=" + eventURL + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
    
    
}
