package com.microservice.inventarioycatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.inventarioycatalogo.model.Stock;
import com.microservice.inventarioycatalogo.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock guardarStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public List<Stock> listarStock() {
        return stockRepository.findAll();
    }

    public Stock buscarStock(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Stock no encontrado"));
    }

    public boolean verificarDisponibilidad(Long idStock, int cantidadSolicitada) {

        Stock stock = buscarStock(idStock);

        return stock.getCantDisponible() >= cantidadSolicitada;
    }

    public Stock ajustarStock(Long idStock, int cantidad) {

        Stock stock = buscarStock(idStock);

        int nuevaCantidad = stock.getCantDisponible() + cantidad;

        if (nuevaCantidad < 0) {
            throw new RuntimeException(
                    "El stock no puede quedar en valores negativos");
        }

        stock.setCantDisponible(nuevaCantidad);

        return stockRepository.save(stock);
    }

    public boolean requiereReabastecimiento(Long idStock) {

        Stock stock = buscarStock(idStock);

        return stock.getCantDisponible() <= stock.getStockMinimo();
    }

    public void eliminarStock(Long id) {

        if (!stockRepository.existsById(id)) {
            throw new RuntimeException("Stock no encontrado");
        }

        stockRepository.deleteById(id);
    }
}
