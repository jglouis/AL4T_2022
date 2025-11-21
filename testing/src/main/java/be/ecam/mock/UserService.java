package be.ecam.mock;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String getUsername(int userId) {
        User user = repository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getName();
    }
}