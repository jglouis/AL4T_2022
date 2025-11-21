package be.ecam.spy;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Test
    void testNotifyUser() {
        // Create a real instance and spy on it
        MessageSender realSender = new MessageSender();
        MessageSender spySender = spy(realSender);

        NotificationService service = new NotificationService(spySender);

        // Act
        service.notifyUser("Alice");

        // Verify that the real method was called with specific argument
        verify(spySender).send("Hello Alice");
    }

    @Test
    void testStubbedSpyMethod() {
        MessageSender realSender = new MessageSender();
        MessageSender spySender = spy(realSender);

        // Stub the behavior: override real logic
        doNothing().when(spySender).send("Hello Bob");

        NotificationService service = new NotificationService(spySender);

        // Act (this no longer prints anything)
        service.notifyUser("Bob");

        verify(spySender).send("Hello Bob"); // Still verify interaction
    }
}