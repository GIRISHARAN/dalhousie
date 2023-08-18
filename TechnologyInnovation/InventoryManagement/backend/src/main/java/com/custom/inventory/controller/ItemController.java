package com.custom.inventory.controller;

import com.custom.inventory.model.Item;
import com.custom.inventory.model.Zone;
import com.custom.inventory.protocol.RequestItem;
import com.custom.inventory.protocol.Response;
import com.custom.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController extends Handler {

    final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    private ResponseEntity<Response> addItem(@RequestBody RequestItem requestItem){
        Zone zone = itemService.updateItem(requestItem);
        Response response = new Response(zone, HttpStatus.OK.value(), HttpStatus.OK.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    private ResponseEntity<Response> updateItemCount(@RequestBody RequestItem requestItem){
        itemService.updateItem(requestItem);

        Response response = new Response("Saved", HttpStatus.OK.value(), HttpStatus.OK.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/delete")
    private ResponseEntity<Response> deleteItem(@RequestBody RequestItem requestItem){
        itemService.deleteItem(requestItem);
        Response response = new Response("Deleted", HttpStatus.OK.value(), HttpStatus.OK.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
