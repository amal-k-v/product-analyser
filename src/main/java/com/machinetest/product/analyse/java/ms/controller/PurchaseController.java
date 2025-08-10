package com.machinetest.product.analyse.java.ms.controller;

import com.machinetest.product.analyse.java.ms.service.PurchaseService;
import com.machinetest.product.analyse.java.ms.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/public")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @PreAuthorize("hasAnyRole('PUBLIC','ADMIN')")
    @PostMapping("/product/buy")
    public ResponseEntity<String> getRevenueByProduct(@RequestParam Long productId,@RequestParam Long quantity){
        return ResponseEntity.ok(purchaseService.buyProduct(productId,quantity));
    }



}
