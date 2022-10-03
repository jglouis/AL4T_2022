package be.ecam.solid.di.bad;

public class AuthenticationService {

    // Singleton
    private static AuthenticationService instance = null;

    private AuthenticationService() {
    }

    /**
     * Retrieve Singleton instance of {@link AuthenticationService}
     *
     * @return the instance of AuthenticationService
     */
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public boolean validateToken() {
        //Some logic
        return true;
    }
}
