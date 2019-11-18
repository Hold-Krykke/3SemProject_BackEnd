package facades;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;

    //Private Constructor to ensure Singleton
    private FacadeExample() {
    }

    /**
     *

     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample() {
        if (instance == null) {
            instance = new FacadeExample();
        }
        return instance;
    }

    //TODO Remove/Change this before use
    public String getFacadeMessage() {
        return "Hello from the facade";
    }

}
