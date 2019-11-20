package facades;

import dto.CityDTO;
import dto.CountryDTO;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public static void setUpClass() { 
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
    

}
