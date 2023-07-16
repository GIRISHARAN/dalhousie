package com.amazon.vpc;

import com.mysql.cj.xdevapi.JsonString;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @GetMapping("/test")
    private ResponseEntity<String> testConnection() {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Running.\"}");
    }

    @PostMapping("/store-products")
    private ResponseEntity<String> storeProducts(@RequestBody ProductListRequest productListRequest) {
        List<Product> productsList = productListRequest.getProducts();
        for(Product product : productsList) {
            System.out.println(product.getName()+"-"+product.getPrice()+"-"+product.isAvailability());
        }
        if(InsertProducts.insertProductsToTable(productsList)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Success.\"}");
        } else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Error Occured.\"}");
        }
    }

    @GetMapping("/list-products")
    private ResponseEntity<ProductListRequest> getProducts() {
        List<Product> products = RetrieveProducts.getProducts();
        ProductListRequest productListRequest = new ProductListRequest();
        productListRequest.setProducts(products);
        return new ResponseEntity<ProductListRequest>(productListRequest,HttpStatus.OK);
    }
}