package com.machinetest.product.analyse.java.ms.controller;

import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
public class ProductAdminController {
    @Autowired
    ProductService productService;

    @PostMapping("/product")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto request){
        String status = productService.addProduct(request);
        return ResponseEntity.ok(status);
    }
    @PutMapping("/product")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto request,@RequestParam Long productId){
        String status = productService.updateProduct(productId,request);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/product")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@RequestParam Long productId){
        String status = productService.deleteProduct(productId);
        return ResponseEntity.ok(status);
    }


    @GetMapping("/product/download-pdf")
    public ResponseEntity<byte[]> productReport(){
        return ResponseEntity.ok(productService.productReport());
    }



}
