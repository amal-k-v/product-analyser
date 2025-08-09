package com.machinetest.product.analyse.java.ms.controller;

import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.service.ProductService;
import com.machinetest.product.analyse.java.ms.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class SaleController {
    @Autowired
    SaleService saleService;


    @GetMapping("/sales/revenue/{productId}")
    public ResponseEntity<Double> getRevenueByProduct(@PathVariable Long productId){
        return ResponseEntity.ok(saleService.getRevenueByProduct(productId));
    }

    @GetMapping("/sales/revenue")
    public ResponseEntity<Double> getRevenue(){
        return ResponseEntity.ok(saleService.getTotalRevenue());
    }

}
