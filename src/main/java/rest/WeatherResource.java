/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.WeatherFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Malte
 */
@Path("weather")
public class WeatherResource {
    
    private WeatherFacade facade;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WeatherResource
     */
    public WeatherResource() {
        facade = WeatherFacade.getWeatherFacade();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return facade.getFacadeMessage();
    }

}
