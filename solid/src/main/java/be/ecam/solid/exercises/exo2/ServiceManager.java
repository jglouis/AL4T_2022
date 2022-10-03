package be.ecam.solid.exercises.exo2;

public class ServiceManager {
    public void performAdminAction(User user) {
        if (!(user instanceof Admin)) {
            throw new RuntimeException("You don't have enough privileges");
        }
        // code for performing the action
    }
}
