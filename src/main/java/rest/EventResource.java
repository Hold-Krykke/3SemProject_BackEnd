package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import facades.CountryFacade;
import facades.EventFacade;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Camilla
 */
@Path("events")
public class EventResource {
    private static final EventFacade FACADE = EventFacade.getEventFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    
    // hvis GET så lav det om til queryparams ellers så lav en post
//    @Path("/{date}/")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getEvents(@PathParam(value = "") throws NotFoundException, URISyntaxException {
//
//        return FACADE.getApiData(Double.NaN, Double.MIN_VALUE, "", "");
//
//    }
}
