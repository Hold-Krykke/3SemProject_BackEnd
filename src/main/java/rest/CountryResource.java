package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityDTO;
import dto.CountryDTO;
import dto.LocationDateDTO;
import errorhandling.NotFoundException;
import facades.CountryFacade;
import facades.EventFacade;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("resource")
public class CountryResource {

    private static final CountryFacade FACADE = CountryFacade.getCountryFacade();
    private static final EventFacade EVENTFACADE = EventFacade.getEventFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}"; //Alternatively use JsonObject
    }

    @Path("facade")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        return "{\"facadeMessage\":\"" + FACADE.getFacadeMessage() + "\"}"; //Alternatively use JsonObject
    }

    /**
     * Endpoint that returns 5 biggest cities in a country.
     *
     * @param country
     * @return List of 5 cities.
     */
    @Path("/{country}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public CountryDTO getCities(@PathParam(value = "country") final String country) throws NotFoundException {

        return FACADE.getCountry(country);

    }
    
    /**
     * Used to get the name of a country given its alpha2 code.
     * @param alpha2
     * @return Name of country
     */
    @Path("countryname/{alpha2}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCountryNameByAlpha2(@PathParam(value = "alpha2") final String alpha2) throws NotFoundException {
        return "{\"Countryname\":\"" + FACADE.getCountryNameByAlpha2(alpha2) + "\"}";
    }

    
    @Path("/events")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String getEvents(LocationDateDTO locationdate)  throws NotFoundException {
        // needs to be changed to be a specific city by name
        CityDTO city = FACADE.getCountry(locationdate.getCountry()).getCities().get(0);
        return EVENTFACADE.getApiData(locationdate, city);

    }
}

//@QueryParam("startdate") String startdate,
//@QueryParam("enddate") String enddate,
//@QueryParam("country") String country,
//@QueryParam("city") String city, 