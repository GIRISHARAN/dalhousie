package com.googlefirestore.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ValidateUserTest {

    @Test
    public void testLoginUserValidation() throws IOException, InterruptedException, ExecutionException {
        // Create a sample login details
        LoginDetails loginDetails = new LoginDetails("testmethod@example.com", "password3");

        // Call the loginUserValidation method and check the result
        boolean result = ValidateUser.loginUserValidation(loginDetails);

        // Assert that the result is false since the provided login details are invalid
        Assertions.assertFalse(() -> result);
    }
}