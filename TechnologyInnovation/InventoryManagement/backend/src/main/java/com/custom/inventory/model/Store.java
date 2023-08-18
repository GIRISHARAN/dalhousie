package com.custom.inventory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "stores")
@CompoundIndexes({
        @CompoundIndex(name = "store_name_email_index", def = "{'name': 1, 'email': 1}", unique = true)
})
public class Store {

    @Id
    private String id;
    private String name;
    private String email;
    private List<Zone> zones;

    public Store(String name, String email, List<Zone> zones) {
        this.name = name;
        this.email = email;
        this.zones = zones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
