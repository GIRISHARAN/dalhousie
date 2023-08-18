package com.custom.inventory.service;

import com.custom.inventory.model.Store;
import com.custom.inventory.model.Zone;
import com.custom.inventory.protocol.RequestZone;
import com.custom.inventory.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZoneService {

    final StoreRepository storeRepository;

    public ZoneService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<String> createZone(RequestZone requestZone){
        Store store = storeRepository.findStore(requestZone.getName(), requestZone.getEmail());

        List<Zone> zones = store.getZones().stream().filter(z -> z.getZoneName().equalsIgnoreCase(requestZone.getZoneName())).toList();
        List<String> zoneNames = new java.util.ArrayList<>();


        if(zones.isEmpty()){
            storeRepository.addZone(requestZone);
            Store updatedStore = storeRepository.findStore(requestZone.getName(), requestZone.getEmail());
            updatedStore.getZones().forEach(zone -> zoneNames.add(zone.getZoneName()));
            return zoneNames;
        }

        store.getZones().forEach(zone -> zoneNames.add(zone.getZoneName()));
        return zoneNames;
    }

    public Zone getZone(RequestZone requestZone){
        Store store1 = storeRepository.findStore(requestZone.getName(), requestZone.getEmail());

        return store1.getZones().stream().filter(z -> z.getZoneName().equalsIgnoreCase(requestZone.getZoneName())).toList().get(0);

    }

    public void deleteZone(RequestZone requestZone){
        storeRepository.deleteZone(requestZone);
    }
}
