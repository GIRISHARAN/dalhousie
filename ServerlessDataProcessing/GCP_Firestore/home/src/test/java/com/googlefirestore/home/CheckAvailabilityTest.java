package com.googlefirestore.home;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class CheckAvailabilityTest {

    @Test
    public void testOnlineCheck() throws IOException, InterruptedException, ExecutionException {
        // Define the user email for which availability needs to be checked
        String userEmail = "test@example.com";

        // Call the onlineCheck method to check the availability of online users
        List<String> availableUsers = CheckAvailability.onlineCheck(userEmail);

        System.out.println(availableUsers.toString());

        // Assert that the availableUsers list is not null
        assertNotNull(availableUsers);

        // Assert that the availableUsers list does not contain the user email being checked
        assertFalse(availableUsers.contains(userEmail));
    }
}
