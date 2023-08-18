package com.custom.inventory.model;

import java.util.ArrayList;

public class Zone {
    private String zoneName;
    private ArrayList<Item> items;

    public Zone(){}
    public Zone(String zoneName, ArrayList<Item> items) {
        this.zoneName = zoneName;
        this.items = items;
    }

    public Zone(String zoneName){
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
