package com.custom.inventory.model;


public class Item {

    private String zoneName;
    private String itemName;
    private Integer count;

    public Item(){}

    public Item(String itemName, Integer count) {
        this.itemName = itemName;
        this.count = count;
    }

    public Item(String zoneName, String itemName, Integer count) {
        this.zoneName = zoneName;
        this.itemName = itemName;
        this.count = count;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
