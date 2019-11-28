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
public class WeatherResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/3SEM/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static WeatherFacade facade;

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
        facade = WeatherFacade.getWeatherFacade();

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
        given().when().get("/weather").then().statusCode(200);
    }

    @Test
    public void testMsg() throws Exception {
        bodyTest("", 200, "msg", "Hello World");
    }

}
