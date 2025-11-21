package be.ecam.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void testGetUsernameSuccess() {
        // Create mock repository
        UserRepository repo = Mockito.mock(UserRepository.class);

        // Define behavior of mock
        when(repo.findById(1)).thenReturn(new User("Alice"));

        // Inject mock into service
        UserService service = new UserService(repo);

        // Execute
        String username = service.getUsername(1);

        // Verify result
        assertEquals("Alice", username);

        // Verify interaction with mock
        verify(repo).findById(1);
    }

    @Test
    void testGetUsernameThrowsExceptionForMissingUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);

        // Return null when user not found
        when(repo.findById(999)).thenReturn(null);

        UserService service = new UserService(repo);

        assertThrows(IllegalArgumentException.class,
                () -> service.getUsername(999));
    }
}