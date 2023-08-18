package com.custom.inventory.protocol;

public class RequestZone {
    private String name;
    private String email;
    private String zoneName;


    public RequestZone(String name, String email, String zoneName) {
        this.name = name;
        this.email = email;
        this.zoneName = zoneName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
