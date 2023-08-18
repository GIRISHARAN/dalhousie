package com.custom.inventory.service;

import com.custom.inventory.model.Store;
import com.custom.inventory.protocol.RequestStore;
import com.custom.inventory.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public List<String> findOrCreateStore(RequestStore requestStore) {
        Store store = storeRepository.findStore(requestStore.getName(), requestStore.getEmail());

        if (store == null) {
            store = storeRepository.createStore(requestStore);
        }
        List<String> zoneNames = new java.util.ArrayList<>();
        store.getZones().forEach(zone -> zoneNames.add(zone.getZoneName()));

        return zoneNames;
    }
}
