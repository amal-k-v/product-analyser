package com.machinetest.product.analyse.java.ms.controller;

import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/public")
public class ProductUserController {
    @Autowired
    ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<Page<ProductEntity>> getAllProduct(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId ){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

}
