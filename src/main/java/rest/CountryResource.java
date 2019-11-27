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
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@OpenAPIDefinition(
        info = @Info(
                title = "Hold Krykke Semesterprojekt API",
                version = "1.0",
                description = "API related to Cphbusiness 3rd semester CS project.",
                contact = @Contact(name = "Github Contributors", url = "https://github.com/Hold-Krykke/3SemProject_BackEnd#contributors")
        ),
        tags = {
            @Tag(name = "Events", description = "API endpoint for Events")
        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/3SEMPROJECT/api/resource/events?"
            ),
            @Server(
                    description = "Server API",
                    url = "https://runivn.dk/3SEMPROJECT/api/resource/events?"
            )

        }
)
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
     * Used to get the events of a given location and date. Instantiates
     * LocationDateDTO and CityDTO from from the data given in the query
     * parameters and the CountryFacade and then uses the DTO's as paramteters
     * for the getApiData().
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
    @Operation(summary = "Get all events in a given city, on given date/dates.",
            tags = {"General"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested list of events"),
                @ApiResponse(responseCode = "400", description = "TODO - [INSERT RIGHT ERROR MESSAGE]")})
    public List<EventDTO> getEvents(@QueryParam("startdate") String startdate,
            @QueryParam("enddate") String enddate,
            @QueryParam("country") String country,
            @QueryParam("city") String city) throws NotFoundException {
        LocationDateDTO locationdate = new LocationDateDTO(startdate, enddate, country, city);
        CityDTO citydto = FACADE.getCountry(country).getSpecificCityByName(city);
        return EVENTFACADE.getApiData(locationdate, citydto);
    }
}
