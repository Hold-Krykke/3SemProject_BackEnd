package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import facades.FacadeExample;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("resource")
public class RenameMeResource {

    private static final FacadeExample FACADE = FacadeExample.getFacadeExample();
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
    public List<String> get5Cities(@PathParam(value = "country") final String country) throws NotFoundException {
        List<String> cities = new ArrayList<>();
        
        if (country.equals("danmark")) {
            cities.add("Copenhagen");
            cities.add("Aarhus");
            cities.add("Odense");
            cities.add("Aalborg");
            cities.add("Roskilde");
        } else {
            throw new NotFoundException("No country found by that name.");
        }
        return cities;
    }

}
