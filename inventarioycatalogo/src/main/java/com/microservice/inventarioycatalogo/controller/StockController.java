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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stock")
@Tag(name = "Stock", description = "Operaciones para la gestión, control y verificación del inventario de stock.")
public class StockController {

        private StockService stockService;

        @PostMapping
        @Operation(summary = "Guardar stock", description = "Registra un nuevo ingreso de stock en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Stock guardado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos del stock inválidos o incompletos")
        })
        public ResponseEntity<?> guardarStock(
                        @Valid @RequestBody Stock stock) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(stockService.guardarStock(stock));
        }

        @GetMapping
        @Operation(summary = "Listar stock", description = "Obtiene una lista de todos los registros de stock en el inventario.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de stock obtenida correctamente")
        })
        public ResponseEntity<?> listarStock() {

                return ResponseEntity.ok(
                                stockService.listarStock());
        }

        @GetMapping("buscar/{id}")
        @Operation(summary = "Buscar stock por ID", description = "Obtiene los detalles del stock correspondiente a un identificador específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Registro de stock encontrado"),
                        @ApiResponse(responseCode = "404", description = "Registro de stock no encontrado")
        })
        public ResponseEntity<?> buscarStock(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                stockService.buscarStock(id));
        }

        @DeleteMapping("eliminar/{id}")
        @Operation(summary = "Eliminar stock", description = "Elimina un registro de stock del sistema mediante su identificador.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Stock eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Registro de stock no encontrado para eliminar")
        })
        public ResponseEntity<?> eliminarStock(
                        @PathVariable Long id) {

                stockService.eliminarStock(id);

                return ResponseEntity.ok(
                                "Stock eliminado correctamente");
        }

        @GetMapping("verificar/{id}/disponibilidad/{cantidad}")
        @Operation(summary = "Verificar disponibilidad de stock", description = "Comprueba si existe stock suficiente para cubrir una cantidad solicitada.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Verificación completada (retorna true o false)"),
                        @ApiResponse(responseCode = "404", description = "Registro de stock no encontrado")
        })
        public ResponseEntity<?> verificarDisponibilidad(
                        @PathVariable Long id,
                        @PathVariable Integer cantidad) {

                boolean disponible = stockService.verificarDisponibilidad(id, cantidad);

                return ResponseEntity.ok(disponible);
        }

        @PutMapping("/{id}/ajustar")
        @Operation(summary = "Ajustar stock", description = "Modifica la cantidad de stock disponible (puede ser un incremento o decremento).")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Stock ajustado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Cantidad de ajuste inválida"),
                        @ApiResponse(responseCode = "404", description = "Registro de stock no encontrado para ajustar")
        })
        public ResponseEntity<?> ajustarStock(
                        @PathVariable Long id,
                        @RequestParam Integer cantidad) {

                return ResponseEntity.ok(
                                stockService.ajustarStock(id, cantidad));
        }

        @GetMapping("/{id}/reabastecimiento")
        @Operation(summary = "Verificar si requiere reabastecimiento", description = "Consulta si el nivel de stock actual está por debajo del umbral mínimo permitido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Verificación completada (retorna true o false)"),
                        @ApiResponse(responseCode = "404", description = "Registro de stock no encontrado")
        })
        public ResponseEntity<?> requiereReabastecimiento(
                        @PathVariable Long id) {

                boolean requiere = stockService.requiereReabastecimiento(id);

                return ResponseEntity.ok(requiere);
        }
}