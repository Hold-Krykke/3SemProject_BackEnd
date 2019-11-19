package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import facades.CountryFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("resource")
public class CountryResource {

    private static final CountryFacade FACADE = CountryFacade.getCountryFacade();
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

}
