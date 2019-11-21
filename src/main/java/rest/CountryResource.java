package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityDTO;
import dto.CountryDTO;
import dto.EventDTO;
import dto.LocationDateDTO;
import facades.EventFacade;
import java.util.List;
import errorhandling.APIUtilException;
import errorhandling.NotFoundException;
import facades.CountryFacade;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("resource")
public class CountryResource {

    private static final EventFacade EVENTFACADE = EventFacade.getEventFacade();
    private static CountryFacade FACADE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public CountryResource() throws APIUtilException {
        FACADE = CountryFacade.getCountryFacade();
    }

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
     *
     * @param alpha2
     * @return Name of country
     */
    @Path("countryname/{alpha2}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCountryNameByAlpha2(@PathParam(value = "alpha2") final String alpha2) throws NotFoundException {
        return "{\"Countryname\":\"" + FACADE.getCountryNameByAlpha2(alpha2) + "\"}";
    }

    /**
     * Used to get the events of a given location and date
     *
     * @param startdate
     * @param enddate
     * @param country
     * @param city
     * @return List of EventDTO
     * @throws NotFoundException
     */
    @Path("/events")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<EventDTO> getEvents(@QueryParam("startdate") String startdate,
            @QueryParam("enddate") String enddate,
            @QueryParam("country") String country,
            @QueryParam("city") String city) throws NotFoundException {
        // needs to be changed to be a specific city by name
//        System.out.println("country " + FACADE.getCountry(locationdate.getCountry()));
//        System.out.println("city " + FACADE.getCountry(locationdate.getCountry()).getCities().get(0));
//        CityDTO citydto = FACADE.getCountry(locationdate.getCountry()).getCities().get(0);
            LocationDateDTO locationdate = new LocationDateDTO(startdate, enddate, country, city);
            //System.out.println("locationdto " + locationdate.toString());
            // 52.51739502, 13.39782715
            //CityDTO cityHardcode = new CityDTO("Berlin", "2350452","52.51739502", "13.39782715");
            CityDTO citydto = FACADE.getCountry(country).getSpecificCityByName(city); // getSpecificCity(city)
                                                    // citydto
        return EVENTFACADE.getApiData(locationdate, citydto);

    }
}

