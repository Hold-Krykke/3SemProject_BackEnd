/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import errorhandling.NotFoundException;
import static io.restassured.RestAssured.given;
import org.glassfish.grizzly.http.util.HttpStatus;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Malte
 */
public class WeatherFacadeTest {

    private static WeatherFacade facade;

    public WeatherFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        facade = WeatherFacade.getWeatherFacade();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getFacadeMessage method, of class WeatherFacade.
     */
    @Test
    public void testGetFacadeMessage() {
        System.out.println("getFacadeMessage");
        String expResult = "Hello World";
        String result = facade.getFacadeMessage();
        assertEquals(expResult, result);
    }

    /*
    Endpoint called: 
    https://ajuhlhansen.dk/WeatherCloud/api/weather/city/Copenhagen/1994/10/04
    Result:
    {
        "code": 400,
        "message": "I dont think we use the same calendar"
    }
     */
    @Test
    public void testGetOldDate() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            facade.getWeather("Copenhagen", "1994", "10", "04");
        });
    }

}
