package com.machinetest.product.analyse.java.ms.repository;

import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}
