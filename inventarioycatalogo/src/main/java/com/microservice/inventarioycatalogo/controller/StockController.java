package com.microservice.inventarioycatalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.inventarioycatalogo.model.Stock;
import com.microservice.inventarioycatalogo.service.StockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private StockService stockService;

    @PostMapping
    public ResponseEntity<?> guardarStock(
            @Valid @RequestBody Stock stock) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stockService.guardarStock(stock));
    }

    @GetMapping
    public ResponseEntity<?> listarStock() {

        return ResponseEntity.ok(
                stockService.listarStock());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarStock(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                stockService.buscarStock(id));
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarStock(
            @PathVariable Long id) {

        stockService.eliminarStock(id);

        return ResponseEntity.ok(
                "Stock eliminado correctamente");
    }

    @GetMapping("verificar/{id}/disponibilidad/{cantidad}")
    public ResponseEntity<?> verificarDisponibilidad(
            @PathVariable Long id,
            @PathVariable Integer cantidad) {

        boolean disponible =
                stockService.verificarDisponibilidad(id, cantidad);

        return ResponseEntity.ok(disponible);
    }

    @PutMapping("/{id}/ajustar")
    public ResponseEntity<?> ajustarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {

        return ResponseEntity.ok(
                stockService.ajustarStock(id, cantidad));
    }

    @GetMapping("/{id}/reabastecimiento")
    public ResponseEntity<?> requiereReabastecimiento(
            @PathVariable Long id) {

        boolean requiere =
                stockService.requiereReabastecimiento(id);

        return ResponseEntity.ok(requiere);
    }
}
