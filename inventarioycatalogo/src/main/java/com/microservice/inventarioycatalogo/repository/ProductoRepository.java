package com.microservice.inventarioycatalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.inventarioycatalogo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
    
}
