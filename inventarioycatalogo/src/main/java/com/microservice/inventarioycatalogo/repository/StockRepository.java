package com.microservice.inventarioycatalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.inventarioycatalogo.model.Stock;

public interface StockRepository extends JpaRepository<Stock,Long> {
    
}
