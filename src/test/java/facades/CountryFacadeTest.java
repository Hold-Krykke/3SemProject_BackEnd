package facades;

import dto.CityDTO;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import javax.ws.rs.WebApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@Disabled
public class CountryFacadeTest {

    private static CountryFacade facade;
    private static CountryDTO denmark;

    public CountryFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        facade = CountryFacade.getCountryFacade();
        denmark = new CountryDTO("Denmark");
        denmark.addCity(new CityDTO("Aarhus"));
        denmark.addCity(new CityDTO("Odense"));
        denmark.addCity(new CityDTO("Aalborg"));
        facade.addCountry(denmark);
    }

    @BeforeEach
    public void setUpTest() {

    }

    @AfterEach
    public void tearDownTest() {

    }

    /**
     * This is a combined test for getEuropeanCountriesAndCities()
     *
     * Since, at facade start we populate it's list of countries, we want to
     * check that it is populated - and somewhat correctly.
     */
    @Test
    public void testFacadeCountries() throws NotFoundException {
        //Arrange
        CountryDTO expResult = new CountryDTO("Sweden", "se");
        CityDTO expResultCity = new CityDTO("Stockholm", "1515017", "59.33258", "18.0649"); //lets hope these values don't change
        expResult.getCities().add(expResultCity);

        //Act
        CountryDTO result = facade.getCountry("Sweden");
        CityDTO resultCity = result.getSpecificCityByName("Stockholm");

        //Assert
        assertTrue(facade.getCountries().size() > 0);
        assertEquals(expResult.getCountryName(), result.getCountryName());
        assertEquals(expResult.getCountryCode(), result.getCountryCode());
        assertEquals(expResultCity, resultCity);
    }

    @Test
    public void testAFacadeMethod() {
        assertEquals("Hello from the facade", facade.getFacadeMessage(), "Expects a facade message");
    }

    @Test
    public void testGetCountry() throws NotFoundException {
        // Arrange
        CountryDTO exp = denmark;
        // Act
        CountryDTO res = facade.getCountry("Denmark");
        // Assert
        assertEquals(exp, res);
    }

    @Test
    public void testGetCountryWrong() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            facade.getCountry("WRONGCOUNTRY");
        });
    }

    @Test
    public void testGetCountryname() throws Exception {
        assertEquals("Denmark", facade.getCountryNameByAlpha2("dk"));
    }

    @Test
    public void testGetCountryNameWrong() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            facade.getCountryNameByAlpha2("WRONGCOUNTRY");
        });
    }

}
