package com.microservice.inventarioycatalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.inventarioycatalogo.model.Producto;
import com.microservice.inventarioycatalogo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con la administración del catálogo y el inventario de productos.")
public class ProductoController {
        @Autowired
        private ProductoService productoService;

        @PostMapping
        @Operation(summary = "Agregar producto", description = "Registra un nuevo producto en el catálogo e inventario.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Producto agregado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos o incompletos")
        })
        public ResponseEntity<?> agregarProducto(
                        @Valid @RequestBody Producto producto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(productoService.agregarProducto(producto));
        }

        @GetMapping
        @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos registrados en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
        })
        public ResponseEntity<?> listarProductos() {
                return ResponseEntity.ok(
                                productoService.listarProductos());
        }

        @GetMapping("buscar/{id}")
        @Operation(summary = "Buscar producto por ID", description = "Obtiene la información detallada de un producto específico mediante su identificador.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Producto no encontrado en el catálogo")
        })
        public ResponseEntity<?> buscarProducto(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                productoService.buscarProducto(id));
        }

        @PutMapping("actualizar/{id}")
        @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos enviados inválidos para la actualización"),
                        @ApiResponse(responseCode = "404", description = "Producto no encontrado para actualizar")
        })
        public ResponseEntity<?> actualizarProducto(
                        @PathVariable Long id,
                        @Valid @RequestBody Producto producto) {

                return ResponseEntity.ok(
                                productoService.actualizarProducto(id, producto));
        }

        @DeleteMapping("eliminar/{id}")
        @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario mediante su identificador.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Producto no encontrado para eliminar")
        })
        public ResponseEntity<?> eliminarProducto(
                        @PathVariable Long id) {

                productoService.eliminarProducto(id);

                return ResponseEntity.ok(
                                "Producto eliminado correctamente");
        }
}