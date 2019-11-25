package facades;

import dto.CityDTO;
import dto.CountryDTO;
import dto.EventDTO;
import dto.LocationDateDTO;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Camilla
 */
public class EventFacadeTest {

    private static EventFacade facade;
    private static LocationDateDTO locationdto;
    private static CityDTO citydto;
    private static LocationDateDTO locationdto_WrongData;
    private static CityDTO citydto_WrongData;
    private static LocationDateDTO locationdto_NoEvents;
    private static CityDTO citydto_NoEvents;

    public EventFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        facade = EventFacade.getEventFacade();
        locationdto = new LocationDateDTO("2020-01-23", "2020-01-23", "Norway", "Oslo");
        citydto = new CityDTO("Oslo", "580000", "59.91273", "10.74609");
        locationdto_WrongData = new LocationDateDTO("2020-01-23", "2020-01-23", "WrongCountry", "WrongCity");
        citydto_WrongData = new CityDTO("WrongCity", "1", "1", "1");
        locationdto_NoEvents = new LocationDateDTO("2019-12-24", "2019-12-24", "Denmark", "Odense");
        citydto_NoEvents = new CityDTO("Odense", "145931", "55.39594", "10.38831");
    }

    /**
     * Test of getApiData method, of class EventFacade.
     */
    @Test
    public void testGetApiData() throws Exception {
        System.out.println("getApiData");
        List<EventDTO> result = facade.getApiData(locationdto, citydto);
        assertTrue(!(result.isEmpty()));
        assertEquals(result.get(0).getEventDate(), "2020-01-23");
        assertEquals((result.get(0).getLatitude().substring(0, 3)), "59.");
        assertEquals((result.get(0).getLongitude().substring(0, 3)), "10.");
    }

    @Test
    public void testGetApiData_WrongData_FAIL() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            facade.getApiData(locationdto_WrongData, citydto_WrongData);
        });
    }
    
    @Test
    public void testGetApiData_NoEvents_FAIL() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            facade.getApiData(locationdto_NoEvents, citydto_NoEvents);
        });
    }
}
