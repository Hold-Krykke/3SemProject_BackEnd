package rest;

import dto.CityDTO;
import dto.CountryDTO;
import facades.CountryFacade;
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
import org.junit.jupiter.api.AfterEach;
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
    private static CountryFacade facade = CountryFacade.getCountryFacade();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        CountryDTO denmark = new CountryDTO("Denmark");
        denmark.addCity(new CityDTO("Koebenhavn"));
        denmark.addCity(new CityDTO("Aalborg"));
        denmark.addCity(new CityDTO("Aarhus"));
        denmark.addCity(new CityDTO("Odense"));
        denmark.addCity(new CityDTO("Roskilde"));
        facade.addCountry(denmark);
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/resource").then().statusCode(200);
    }

    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testFacadeMessage() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/facade").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("facadeMessage", equalTo("Hello from the facade"));
    }

    @Test
    public void testWrongGetCountry() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/WRONG").then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("No country by that name exists."));
    }

    @Test
    public void testGetCountry() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/Denmark").then()
                .assertThat()
                .statusCode(200)
                .body("cities[0].cityName", equalTo("Koebenhavn"))
                .body("cities[1].cityName", equalTo("Aalborg"))
                .body("cities[2].cityName", equalTo("Aarhus"))
                .body("cities[3].cityName", equalTo("Odense"))
                .body("cities[4].cityName", equalTo("Roskilde"));
    }

    @Test
    public void testCountryName() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/countryname/DK").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("Countryname", equalTo("Denmark"));
    }

    @Test
    public void testCountryNameWrong() throws Exception {
        given()
                .contentType("application/json")
                .get("/resource/countryname/WRONG").then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("No country with given alpha2 code found"));
    }

    
    /**
     * Test of getEvents method, of class CountryResource.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEvents() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/resource/events").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("events", equalTo(""));
    }
}
