package rest;

import dto.WeatherDTO;
import facades.WeatherFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@Disabled
public class WeatherResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/3SEM/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static WeatherFacade facade;

    // Change this, if you change URL for country resource endpoint. 
    private final String url = "/weather";
    private final String testDate = "/city/Copenhagen/1994/10/04";

    private WeatherDTO weatherOne;
    private WeatherDTO weatherTwo;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        facade = WeatherFacade.getWeatherFacade();

    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {

        weatherOne = new WeatherDTO("04.10.2017 09:22", "Remember your umbrella today!", "https://www.metaweather.com/static/img/weather/png/lr.png", "Light Rain", "WSW", 79, 75, 12.809999999999999, 15.574903598929453);
        weatherTwo = new WeatherDTO("04.10.2017 12:22", "An umbrella is not going to be enough, take a cap or drown in the rain", "https://www.metaweather.com/static/img/weather/png/hr.png", "Heavy Rain", "WSW", 83, 77, 12.29, 15.488408930315757);

    }

    /**
     * A REST test with body.
     *
     * @param url of the endpoint after this.url
     * @param code HTTPStatusCode
     * @param key of the JSON body.
     * @param value of the JSON body.
     */
    public void bodyTest(String url, int code, String key, String value) {
        given()
                .contentType("application/json")
                .get(this.url + url).then()
                .assertThat()
                .statusCode(code)
                .body(key, equalTo(value));

    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get(this.url).then().statusCode(200);
    }

    @Test
    public void testGetWeatherWrongDate() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .get(url + "/city/Copenhagen/1994/10/04").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo("I dont think we use the same calendar"));
    }

    @Test
    public void testGetWeatherData() {
        bodyTest(url + testDate, 200, "[0].dateTime", weatherOne.getDateTime());
        bodyTest(url + testDate, 200, "[0].funnyAdvice", weatherOne.getFunnyAdvice());
        bodyTest(url + testDate, 200, "[0].weatherIcon", weatherOne.getWeatherIcon());
        bodyTest(url + testDate, 200, "[0].weatherStatus", weatherOne.getWeatherStatus());
        bodyTest(url + testDate, 200, "[0].windDirection", weatherOne.getWindDirection());
        
    }

}
