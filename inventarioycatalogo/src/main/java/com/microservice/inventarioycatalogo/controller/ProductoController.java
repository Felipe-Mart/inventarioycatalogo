package com.microservice.inventarioycatalogo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.inventarioycatalogo.model.Producto;
import com.microservice.inventarioycatalogo.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> agregarProducto(
            @Valid @RequestBody Producto producto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.agregarProducto(producto));
    }

    @GetMapping
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(
                productoService.listarProductos());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarProducto(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productoService.buscarProducto(id));
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        return ResponseEntity.ok(
                productoService.actualizarProducto(id, producto));
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable Long id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.ok(
                "Producto eliminado correctamente");
    }
}
