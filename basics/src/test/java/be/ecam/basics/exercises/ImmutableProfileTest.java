package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableProfileTest {

    @Test
    void mutatingReturnedRolesShouldNotAffectProfile() {
        List<String> roles = new ArrayList<>(Arrays.asList("USER"));
        ImmutableProfile profile = new ImmutableProfile("alice", roles);
        assertThrows(UnsupportedOperationException.class, () -> profile.getRoles().add("ADMIN"));
    }

    @Test
    void mutatingOriginalListShouldNotAffectProfile() {
        List<String> roles = new ArrayList<>(Arrays.asList("USER"));
        ImmutableProfile profile = new ImmutableProfile("alice", roles);
        roles.add("ADMIN");
        assertEquals(Collections.singletonList("USER"), profile.getRoles());
    }
}
