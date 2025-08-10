package com.machinetest.product.analyse.java.ms.controller;

import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.model.ProductResponse;
import com.machinetest.product.analyse.java.ms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
public class ProductAdminController {
    @Autowired
    ProductService productService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductDto request){
        ProductResponse response = productService.addProduct(request);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto request,@RequestParam Long productId){
        ProductDto productDto = productService.updateProduct(productId, request);
        return ResponseEntity.ok(productDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/product")
    public ResponseEntity<String> deleteProduct(@RequestParam Long productId){
        String status = productService.deleteProduct(productId);
        return ResponseEntity.ok(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/download-pdf")
    public ResponseEntity<byte[]> productReport(){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products_report.pdf")
                .body(productService.productReport());
    }



}
