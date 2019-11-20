package utils;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class APIUtilTest {

    public APIUtilTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getApiData method, of class APIUtil.
     */
    @Test
    public void testGetApiData() throws Exception {
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

}
