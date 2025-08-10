package com.machinetest.product.analyse.java.ms.service;

import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.entity.SaleEntity;
import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.repository.ProductRepo;
import com.machinetest.product.analyse.java.ms.repository.SaleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService {

    private final ProductRepo productRepo;
    private final SaleRepo saleRepo;


    public double getTotalRevenue() {
        return saleRepo.findAll().stream()
                .mapToDouble(sale ->
                        productRepo.findById(sale.getProductId())
                                .map(product -> product.getPrice() * sale.getQuantity())
                                .orElse(0.0)
                )
                .sum();
    }

    public double getRevenueByProduct(Long productId) {
        List<SaleEntity> sales = saleRepo.findByProductId(productId);
        ProductEntity product = productRepo.findById(productId).orElse(null);
        if (product == null) return 0;

        return sales.stream()
                .mapToDouble(sale -> product.getPrice() * sale.getQuantity())
                .sum();
    }




}
