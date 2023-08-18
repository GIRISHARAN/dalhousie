package com.custom.inventory.controller;

import com.custom.inventory.model.Store;
import com.custom.inventory.protocol.RequestStore;
import com.custom.inventory.protocol.Response;
import com.custom.inventory.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @PostMapping("/open")
    private ResponseEntity<Response> openStore(@RequestBody RequestStore requestStore){
        List<String> zoneNames = storeService.findOrCreateStore(requestStore);
        Response response = new Response(zoneNames, HttpStatus.OK.value(), HttpStatus.OK.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
