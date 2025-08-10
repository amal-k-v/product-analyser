package com.machinetest.product.analyse.java.ms.service;

import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.entity.SaleEntity;
import com.machinetest.product.analyse.java.ms.exception.BussinessException;
import com.machinetest.product.analyse.java.ms.repository.ProductRepo;
import com.machinetest.product.analyse.java.ms.repository.SaleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {

    private final ProductRepo productRepo;
    private final SaleRepo saleRepo;



  //In the main method, create a few Product objects and add them to the ProductService.
  // Create a few Sale objects and add them to the SaleService.
    public String buyProduct(Long productId,Long  quantity) {
        ProductEntity product = productRepo.findById(productId).orElse(null);
        if (Objects.nonNull(product)){
            Long currentQuantity = product.getQuantity();
            if(currentQuantity>0) {
                Long updatedQuantity = currentQuantity -quantity;
                product.setQuantity(updatedQuantity);
                productRepo.save(product);

            }else {
                throw  new BussinessException("Product is not available in this moment");
            }

        }

            SaleEntity saleEntity=new SaleEntity();
            saleEntity.setProductId(productId);
            saleEntity.setQuantity(quantity);
            saleEntity.setSaleDate(LocalDate.now());
            saleRepo.save(saleEntity);


   return "SUCCESS";

    }




}
