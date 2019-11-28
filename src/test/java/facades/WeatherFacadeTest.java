/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.WeatherDTO;
import errorhandling.NotFoundException;
import static io.restassured.RestAssured.given;
import java.util.List;
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
    
    private WeatherDTO weatherOne;
    private WeatherDTO weatherTwo;

    public WeatherFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        facade = WeatherFacade.getWeatherFacade();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    /*
    [
    {
    "dateTime":"04.10.2017 09:22",
    "funnyAdvice":"Remember your umbrella today!",
    "humidity":79,
    "predictability":75,
    "temp":12.809999999999999,
    "weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png",
    "weatherStatus":"Light Rain",
    "windDirection":"WSW",
    "windSpeed":15.574903598929453
    },{
    "dateTime":"04.10.2017 12:22",
    "funnyAdvice":"An umbrella is not going to be enough, take a cap or drown in the rain",
    "humidity":83,
    "predictability":77,
    "temp":12.29,
    "weatherIcon":"https://www.metaweather.com/static/img/weather/png/hr.png",
    "weatherStatus":"Heavy Rain","windDirection":"WSW","windSpeed":15.488408930315757
    },{
    "dateTime":"04.10.2017 15:22","funnyAdvice":"Remember your umbrella today!","humidity":83,"predictability":75,"temp":12.3,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":15.095879450315755},{"dateTime":"04.10.2017 18:22","funnyAdvice":"Remember your umbrella today!","humidity":83,"predictability":75,"temp":11.21,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":15.334485988134892},{"dateTime":"04.10.2017 21:22","funnyAdvice":"Remember your umbrella today!","humidity":84,"predictability":75,"temp":11.21,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":14.942485988134894}]
    */
    @BeforeEach
    public void setUp() {
        
        weatherOne = new WeatherDTO("04.10.2017 09:22", "Remember your umbrella today!", "https://www.metaweather.com/static/img/weather/png/lr.png", "Light Rain", "WSW", 79, 75, 12.809999999999999, 15.574903598929453);
        weatherTwo = new WeatherDTO("04.10.2017 12:22", "An umbrella is not going to be enough, take a cap or drown in the rain", "https://www.metaweather.com/static/img/weather/png/hr.png", "Heavy Rain", "WSW", 83, 77, 12.29, 15.488408930315757);
        
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
    
    /*
    [{"dateTime":"04.10.2017 09:22","funnyAdvice":"Remember your umbrella today!","humidity":79,"predictability":75,"temp":12.809999999999999,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":15.574903598929453},{"dateTime":"04.10.2017 12:22","funnyAdvice":"An umbrella is not going to be enough, take a cap or drown in the rain","humidity":83,"predictability":77,"temp":12.29,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/hr.png","weatherStatus":"Heavy Rain","windDirection":"WSW","windSpeed":15.488408930315757},{"dateTime":"04.10.2017 15:22","funnyAdvice":"Remember your umbrella today!","humidity":83,"predictability":75,"temp":12.3,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":15.095879450315755},{"dateTime":"04.10.2017 18:22","funnyAdvice":"Remember your umbrella today!","humidity":83,"predictability":75,"temp":11.21,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":15.334485988134892},{"dateTime":"04.10.2017 21:22","funnyAdvice":"Remember your umbrella today!","humidity":84,"predictability":75,"temp":11.21,"weatherIcon":"https://www.metaweather.com/static/img/weather/png/lr.png","weatherStatus":"Light Rain","windDirection":"WSW","windSpeed":14.942485988134894}]
    */
    @Test
    public void testGetRightDate() throws NotFoundException {
        List<WeatherDTO> res = facade.getWeather("Copenhagen", "2017", "10", "04");
        assertTrue(res.contains(weatherOne));
        assertTrue(res.contains(weatherTwo));
    }

}
