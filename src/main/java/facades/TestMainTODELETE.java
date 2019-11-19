package facades;

import com.google.gson.Gson;

/**
 *
 * @author Camilla
 */
public class TestMainTODELETE {
    public static void main(String[] args) throws Exception {
        EventFacade facade = new EventFacade();
        Gson gson = new Gson();
        System.out.println(gson.toJson(facade.getApiData("https://app.ticketmaster.com/discovery/v2/events.json?", "DK", 
                "Copenhagen", "2019-11-23T00:00:00Z", "2019-11-23T23:59:59Z" )));
        
        // (String url, String countrycode, String city, String startdate, String enddate)
        // 2015-02-01T10:00:00Z
        // 2019-11-23T23:59:59Z
    }
}