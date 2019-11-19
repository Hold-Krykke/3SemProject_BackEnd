package facades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//@Disabled
public class FacadeExampleTest {

    private static CountryFacade facade;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClassV2() { 
        facade = CountryFacade.getCountryFacade();
    }

    @Test
    public void testAFacadeMethod() {
        assertEquals("Hello from the facade", facade.getFacadeMessage(), "Expects a facade message");
    }

}
