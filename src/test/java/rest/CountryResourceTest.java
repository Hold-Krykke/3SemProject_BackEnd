package rest;

import dto.CityDTO;
import dto.CountryDTO;
import facades.CountryFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@Disabled
public class CountryResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/3SEM/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static CountryFacade facade;
    private static List<String> denmarkCities = new ArrayList<>();
    private static String payload;
    private static String payload_WrongData;
    private static String payload_NoEvents;

    // Change this, if you change URL for country resource endpoint. 
    private final String url = "/resource";

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
        facade = CountryFacade.getCountryFacade();

        denmarkCities.add("Koebenhavn");
        denmarkCities.add("Aalborg");
        denmarkCities.add("Aarhus");
        denmarkCities.add("Odense");
        denmarkCities.add("Roskilde");

        CountryDTO denmark = new CountryDTO("Denmark");
        denmarkCities.forEach((city) -> {
            denmark.addCity(new CityDTO(city));
        });
        facade.addCountry(denmark);

        payload = "?startdate=2019-12-24&enddate=2020-12-24&country=Norway&city=Oslo";
        payload_WrongData = "?startdate=2019-12-24&enddate=2020-12-24&country=Wrong&city=Oslo";
        payload_NoEvents = "?startdate=2019-12-24&enddate=2019-12-24&country=Norway&city=Stavanger";
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
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
        given().when().get("/resource").then().statusCode(200);
    }

    @Test
    public void testDummyMsg() throws Exception {
        bodyTest("", 200, "msg", "Hello World");
    }

    @Test
    public void testFacadeMessage() throws Exception {
        bodyTest("/facade", 200, "facadeMessage", "Hello from the facade");
    }

    @Test
    public void testWrongGetCountry() throws Exception {
        bodyTest("/WRONG", 400, "message", "No country by that name exists");
    }

    @Test
    public void testGetCountry() throws Exception {
//        given()
//                .contentType("application/json")
//                .get(url + "/Denmark").then()
//                .assertThat()
//                .statusCode(200)
//                .body("cities[0].cityName", equalTo("Koebenhavn"))
//                .body("cities[1].cityName", equalTo("Aalborg"))
//                .body("cities[2].cityName", equalTo("Aarhus"))
//                .body("cities[3].cityName", equalTo("Odense"))
//                .body("cities[4].cityName", equalTo("Roskilde"));
        // Not actually sure if this is better or worse. 
        for (int i = 0; i < denmarkCities.size(); i++) {
            bodyTest("/Denmark", 200, "cities[" + i + "].cityName", denmarkCities.get(i));
        }
    }

    @Test
    public void testCountryName() throws Exception {
        bodyTest("/countryname/DK", 200, "Countryname", "Denmark");
    }

    @Test
    public void testCountryNameWrong() throws Exception {
        bodyTest("/countryname/WRONG", 400, "message", "No country with given alpha2 code found");
    }

    /**
     * Test of getEvents method, of class CountryResource.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEvents() throws Exception {
        Thread.sleep(1000);
        given()
                .contentType("application/json")
                .accept("application/json")
                .get("/resource/events" + payload).then()
                .assertThat().log().body()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].eventAddress", notNullValue())
                .body("[0].eventDate", notNullValue())
                .body("[0].eventName", notNullValue())
                .body("[0].eventURL", notNullValue())
                .body("[0].latitude", notNullValue())
                .body("[0].longitude", notNullValue());
    }

    /**
     * FAIL Test of getEvents method, of class CountryResource.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEvents_WrongData_FAIL() throws Exception {
        Thread.sleep(1000);
        given()
                .contentType("application/json")
                .accept("application/json")
                .get("/resource/events" + payload_WrongData).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("No country by that name exists"));
    }

    /**
     * FAILTest of getEvents method, of class CountryResource.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEvents_NoEvents_FAIL() throws Exception {
        Thread.sleep(1000);
        given()
                .contentType("application/json")
                .accept("application/json")
                .get("/resource/events" + payload_NoEvents).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("No events found at all"));
    }
}
