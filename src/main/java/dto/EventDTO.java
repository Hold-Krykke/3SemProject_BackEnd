package dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Camilla
 */
@Schema(name = "Event", description = "Returns the field or 'N/A' if field is not available.")
public class EventDTO {

    @Schema(example = "\"eventName\":\"The Star Wars Music\"")
    private String eventName;
    @Schema(example = "\"eventDate\":\"2019-11-27\"")
    private String eventDate;
    @Schema(example = "\"eventAddress\":\"Oslo Konserthus, Munkedamsveien 14\"")
    private String eventAddress;
    @Schema(example = "\"eventURL\":\"https://www.ticketmaster.no/event/the-star-wars-music-tickets/603869?language=en-us\"")
    private String eventURL;
    @Schema(example = "\"latitude\":\"59.91324\"")
    private String latitude;
    @Schema(example = "\"longitude\":\"10.72963\"")
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
