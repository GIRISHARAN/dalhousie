package com.custom.inventory.service;

import com.custom.inventory.model.Item;
import com.custom.inventory.model.Store;
import com.custom.inventory.model.Zone;
import com.custom.inventory.protocol.RequestItem;
import com.custom.inventory.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemService {

    final StoreRepository storeRepository;

    public ItemService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Zone updateItem(RequestItem requestItem) {
        Store store = storeRepository.findStore(requestItem.getName(), requestItem.getEmail());

        Zone zone = store.getZones().stream().filter(z -> z.getZoneName().equalsIgnoreCase(requestItem.getZoneName()))
                .toList().get(0);

        List<Item> items = zone.getItems().stream()
                .filter(i -> i.getItemName().equalsIgnoreCase(requestItem.getItemName())).toList();

        if (items.isEmpty()) {
            storeRepository.addItemToZone(requestItem);
        } else {
            storeRepository.updateItemCountInZone(requestItem);
        }

        Store store1 = storeRepository.findStore(requestItem.getName(), requestItem.getEmail());

        return store1.getZones().stream().filter(z -> z.getZoneName().equalsIgnoreCase(requestItem.getZoneName()))
                .toList().get(0);
    }

    public void deleteItem(RequestItem requestItem) {
        storeRepository.deleteItem(requestItem);
    }
}
