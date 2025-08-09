package com.machinetest.product.analyse.java.ms.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.entity.SaleEntity;
import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.repository.ProductRepo;
import com.machinetest.product.analyse.java.ms.repository.SaleRepo;
import com.machinetest.product.analyse.java.ms.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final SaleRepo saleRepo;
    private final PdfGenerator pdfGenerator;

    public String addProduct(ProductDto productDto){
        String status="";
        try {
            ProductEntity productEntity=new ProductEntity();
            BeanUtils.copyProperties(productDto,productEntity);
            productRepo.save(productEntity);
            status="SUCCESS";
        }catch (Exception e){
            status="FAILED";
            log.error("Error Found While Adding Product {}",e.getLocalizedMessage());
        }
        return  status;
    }

    public Page<ProductEntity> getAllProducts(int page,int size){
        Pageable pageable= PageRequest.of(page,size);

        return productRepo.findAll(pageable);
    }


    public ProductDto  getProductById(Long id){
        ProductDto response=new ProductDto();
        try {
        Optional<ProductEntity> product = productRepo.findById(id);
        product.ifPresentOrElse(
                productEntity -> {
                   BeanUtils.copyProperties(productEntity,response);
                },
                () -> {
                    throw new RuntimeException("Product not found!");
                }
        );
        } catch (Exception e) {
            log.error("Error Found While Fetching Product {}", e.getLocalizedMessage());
            throw e;
        }
        return response;
    }


    public String updateProduct(Long id, ProductDto request) {
        String status = "";
        try {
            Optional<ProductEntity> product = productRepo.findById(id);
            product.ifPresentOrElse(
                    productEntity -> {
                        BeanUtils.copyProperties(request, productEntity,UtilService.getNullPropertyNames(request));
                        productRepo.save(productEntity);
                    },
                    () -> {
                        throw new RuntimeException("Product not found!");
                    }
            );
            status = "SUCCESS";

        } catch (Exception e) {
            status = "FAILED";
            log.error("Error Found While updating Product {}", e.getLocalizedMessage());
            throw e;
        }
        return status;
    }

    public String deleteProduct(Long id){
          String status = "";
          try {
               productRepo.deleteById(id);
              status = "SUCCESS";

          } catch (Exception e) {
              status = "FAILED";
              log.error("Error Found While Deleting  Product {}", e.getLocalizedMessage());
          }
          return status;
      }

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

    public byte[] productReport(){
        List<ProductEntity> products = productRepo.findAll();
        ByteArrayInputStream stream = pdfGenerator.generateProductPdf(products);
        return  stream.readAllBytes();
    }



}
