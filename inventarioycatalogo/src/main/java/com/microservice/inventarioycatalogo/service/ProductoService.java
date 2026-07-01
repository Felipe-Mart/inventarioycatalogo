package com.microservice.inventarioycatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.inventarioycatalogo.model.Producto;
import com.microservice.inventarioycatalogo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(Producto producto) {

        if (producto.getStock() != null) {
            producto.getStock().setProducto(producto);
        }

        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProducto(Long id) {
    return productoRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Producto no encontrado"));
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {

        Producto producto = buscarProducto(id);

        producto.setNombProducto(
                productoActualizado.getNombProducto());

        producto.setPrecio(
                productoActualizado.getPrecio());

        if (productoActualizado.getStock() != null) {
            producto.setStock(productoActualizado.getStock());
            producto.getStock().setProducto(producto);
        }

        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException(
                    "Producto no encontrado");
        }

        productoRepository.deleteById(id);
    }
}
