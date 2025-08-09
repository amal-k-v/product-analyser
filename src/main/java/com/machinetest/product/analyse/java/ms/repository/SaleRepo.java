package com.machinetest.product.analyse.java.ms.repository;

import com.machinetest.product.analyse.java.ms.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity,Long> {
    List<SaleEntity> findByProductId(Long id);
}
