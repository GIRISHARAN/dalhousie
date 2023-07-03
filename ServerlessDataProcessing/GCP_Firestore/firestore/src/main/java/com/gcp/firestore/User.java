package com.gcp.firestore;

import jakarta.annotation.Nonnull;

/**
 * Represents a user in the system.
 */
public class User {

    private String name;
    @Nonnull
    private String password;
    @Nonnull
    private String email;
    private String location;

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    /**
     * Constructs a User object with the provided information.
     *
     * @param name     The full name of the user.
     * @param password The password of the user.
     * @param email    The email address of the user.
     * @param location The location of the user.
     */
    public User(String name, String password, String email, String location) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.location = location;
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the location of the user.
     *
     * @return The location of the user.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the user.
     *
     * @param location The location of the user.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}