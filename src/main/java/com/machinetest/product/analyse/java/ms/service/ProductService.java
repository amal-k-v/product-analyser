package com.machinetest.product.analyse.java.ms.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import com.machinetest.product.analyse.java.ms.entity.SaleEntity;
import com.machinetest.product.analyse.java.ms.exception.BussinessException;
import com.machinetest.product.analyse.java.ms.model.ProductDto;
import com.machinetest.product.analyse.java.ms.model.ProductResponse;
import com.machinetest.product.analyse.java.ms.repository.ProductRepo;
import com.machinetest.product.analyse.java.ms.repository.SaleRepo;
import com.machinetest.product.analyse.java.ms.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
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
public class ProductService {

    private final ProductRepo productRepo;
    private final SaleRepo saleRepo;
    private final PdfGenerator pdfGenerator;

    public ProductResponse addProduct(ProductDto productDto){
        ProductResponse response=new ProductResponse();
        String status="";
        ProductEntity product=null;
        try {
            ProductEntity productEntity=new ProductEntity();
            BeanUtils.copyProperties(productDto,productEntity);
             product = productRepo.save(productEntity);
            status="SUCCESS";
        }catch (Exception e){
            status="FAILED";
            log.error("Error Found While Adding Product {}",e.getLocalizedMessage());
        }
        response.setStatus(status);
        if(Objects.nonNull(product)) {
            response.setId(product.getId());
        }

        return  response;
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
                    throw new BussinessException("Product not found!");
                }
        );
        } catch (Exception e) {
            log.error("Error Found While Fetching Product {}", e.getLocalizedMessage());
            throw e;
        }
        return response;
    }


    public ProductDto updateProduct(Long id, ProductDto request) {
       ProductDto response=new ProductDto();
        try {
            Optional<ProductEntity> product = productRepo.findById(id);
            product.ifPresentOrElse(
                    productEntity -> {
                        BeanUtils.copyProperties(request, productEntity,UtilService.getNullPropertyNames(request));
                        ProductEntity prd = productRepo.save(productEntity);
                        BeanUtils.copyProperties(prd,response);
                    },
                    () -> {
                        throw new BussinessException("Product not found!");
                    }
            );


        } catch (Exception e) {

            log.error("Error Found While updating Product {}", e.getLocalizedMessage());
            throw e;
        }
        return response;
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


    public byte[] productReport(){
        List<ProductEntity> products = productRepo.findAll();
        ByteArrayInputStream stream = pdfGenerator.generateProductPdf(products);
        return  stream.readAllBytes();
    }



}
