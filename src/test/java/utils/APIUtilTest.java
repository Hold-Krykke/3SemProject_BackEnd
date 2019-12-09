package utils;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

public class APIUtilTest {

    public APIUtilTest() {
    }

    /**
     * Test of getApiData method, of class APIUtil.
     *
     * Tests a single endpoint with only one object.
     */

    @Test
    public void testGetApiData_SingleEndpoint_Object() throws Exception {
        //Arrange
        APIUtil instance = new APIUtil();
        ArrayList<String> endpoints = new ArrayList();
        endpoints.add("http://api.icndb.com/jokes/15");
        List<String> expResult = new ArrayList();
        expResult.add("{\"type\":\"success\",\"value\":{\"id\":15,\"joke\":\"When Chuck Norris goes to donate blood, he declines the syringe, and instead requests a hand gun and a bucket.\",\"categories\":[]}}");

        //Act
        List<String> result = instance.getApiData(endpoints);

        //Assert
        assertEquals(expResult, result);

    }

    /**
     * Test of getApiData method, of class APIUtil.
     *
     * Tests a single endpoint with multiple objects.
     */
    @Test
    public void testGetApiData_SingleEndpoint_Array() throws Exception {
        //Arrange
        APIUtil instance = new APIUtil();
        ArrayList<String> endpoints = new ArrayList();
        endpoints.add("https://catfact.ninja/breeds?limit=2");
        List<String> expResult = new ArrayList();
        expResult.add("{\"current_page\":1,\"data\":[{\"breed\":\"Abyssinian\",\"country\":\"Ethiopia\",\"origin\":\"Natural/Standard\",\"coat\":\"Short\",\"pattern\":\"Ticked\"},{\"breed\":\"Aegean\",\"country\":\"Greece\",\"origin\":\"Natural/Standard\",\"coat\":\"Semi-long\",\"pattern\":\"Bi- or tri-colored\"}],\"first_page_url\":\"https://catfact.ninja/breeds?page=1\",\"from\":1,\"last_page\":49,\"last_page_url\":\"https://catfact.ninja/breeds?page=49\",\"next_page_url\":\"https://catfact.ninja/breeds?page=2\",\"path\":\"https://catfact.ninja/breeds\",\"per_page\":\"2\",\"prev_page_url\":null,\"to\":2,\"total\":98}");

        //Act
        List<String> result = instance.getApiData(endpoints);

        //Assert
        assertEquals(expResult, result);
    }

    /**
     * Test of getApiData method, of class APIUtil.
     *
     * Tests multiple endpoints, all with single objects.
     */
    @Test
    public void testGetApiData_MultipleEndpoint_Object() throws Exception {
        //Arrange
        APIUtil instance = new APIUtil();
        ArrayList<String> endpoints = new ArrayList();
        endpoints.add("http://api.icndb.com/jokes/16");
        endpoints.add("http://api.icndb.com/jokes/17");
        List<String> expResult = new ArrayList();
        expResult.add("{\"type\":\"success\",\"value\":{\"id\":16,\"joke\":\"Pluto is actually an orbiting group of British soldiers from the American Revolution who entered space after the Chuck gave them a roundhouse kick to the face.\",\"categories\":[]}}");
        expResult.add("{\"type\":\"success\",\"value\":{\"id\":17,\"joke\":\"Chuck Norris does not teabag the ladies. He potato-sacks them.\",\"categories\":[\"explicit\"]}}");

        //Act
        List<String> result = instance.getApiData(endpoints);

        //Assert
        assertEquals(expResult.size(), result.size());
        assertTrue(expResult.containsAll(result)); //We can never guarantee the location due to the threads. Sometimes it is [joke16,joke17] but not always.
        assertTrue(result.containsAll(expResult)); //This is a way to check both lists - are they equal?
    }

    /**
     * Test of getApiData method, of class APIUtil.
     *
     * Tests multiple endpoints, all with multiple objects.
     */
    @Disabled("This test is disabled until api.quotable.io is available again.")
    @Test
    public void testGetApiData_MultipleEndpoint_Array() throws Exception {
        //Arrange
        APIUtil instance = new APIUtil();
        ArrayList<String> endpoints = new ArrayList();
        endpoints.add("https://catfact.ninja/breeds?limit=2");
        endpoints.add("https://api.quotable.io/quotes?&limit=2");
        List<String> expResult = new ArrayList();
        expResult.add("{\"current_page\":1,\"data\":[{\"breed\":\"Abyssinian\",\"country\":\"Ethiopia\",\"origin\":\"Natural/Standard\",\"coat\":\"Short\",\"pattern\":\"Ticked\"},{\"breed\":\"Aegean\",\"country\":\"Greece\",\"origin\":\"Natural/Standard\",\"coat\":\"Semi-long\",\"pattern\":\"Bi- or tri-colored\"}],\"first_page_url\":\"https://catfact.ninja/breeds?page=1\",\"from\":1,\"last_page\":49,\"last_page_url\":\"https://catfact.ninja/breeds?page=49\",\"next_page_url\":\"https://catfact.ninja/breeds?page=2\",\"path\":\"https://catfact.ninja/breeds\",\"per_page\":\"2\",\"prev_page_url\":null,\"to\":2,\"total\":98}");
        expResult.add("{\"count\":2,\"totalCount\":1604,\"lastItemIndex\":2,\"results\":[{\"_id\":\"-14YplwiKmh\",\"content\":\"A short saying often contains much wisdom.\",\"author\":\"Sophocles\"},{\"_id\":\"-CXvcGgw_i0X\",\"content\":\"The eye sees only what the mind is prepared to comprehend.\",\"author\":\"Henri Bergson\"}]}");
        //Act
        List<String> result = instance.getApiData(endpoints);
        
        //Assert
        assertEquals(expResult.size(), result.size());
        //assertEquals(expResult,result);
        assertTrue(expResult.containsAll(result)); //We can never guarantee the location due to the threads. Sometimes it is [joke16,joke17] but not always.
        assertTrue(result.containsAll(expResult)); //This is a way to check both lists - are they equal?
    }

}
