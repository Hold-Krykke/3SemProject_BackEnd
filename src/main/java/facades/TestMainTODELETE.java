package facades;

import com.google.gson.Gson;

/**
 *
 * @author Camilla
 */
public class TestMainTODELETE {
    private static final EventFacade FACADE = EventFacade.getEventFacade();
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        System.out.println(FACADE.getApiData(55.681554, 12.591135, "2019-11-23T00:00:00Z", "2019-11-25T23:59:59Z" ));
        
        // https://app.ticketmaster.com/discovery/v2/events.json?
        // (Double latitude, Double longitude, String url, String startdate, String enddate)
        // (Double latitude, Double longitude, String url, String countrycode, String startdate, String enddate)
        // 2015-02-01T10:00:00Z
        // 2019-11-23T23:59:59Z
//         lat         long
//         55.681554, 12.591135
    }
}