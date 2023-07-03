package com.googlefirestore.login;

import io.micrometer.common.lang.NonNull;

/**
 * Class representing login details of a user.
 */
public class LoginDetails {

    @NonNull
    private String email;
    @NonNull
    private String password;

    /**
     * Default constructor for LoginDetails.
     */
    public LoginDetails() {
    }

    /**
     * Constructor for LoginDetails.
     *
     * @param email    the email of the user
     * @param password the password of the user
     */
    public LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Retrieves the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}