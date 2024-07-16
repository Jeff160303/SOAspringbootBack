package com.deliveryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listarTodasLasVentas() {
        List<Venta> ventas = ventaService.listarTodasLasVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Venta>> listarVentasPorDni(@PathVariable String dni) {
        List<Venta> ventas = ventaService.listarVentasPorDni(dni);
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/estado/{idVenta}")
    public ResponseEntity<Venta> actualizarEstado(@PathVariable int idVenta, @RequestParam String estado) {
        Venta ventaActualizada = ventaService.actualizarEstado(idVenta, estado);
        return ResponseEntity.ok(ventaActualizada);
    }

    @GetMapping("/{idVenta}/detalles")
    public ResponseEntity<List<DetalleVenta>> obtenerDetallesDeVenta(@PathVariable int idVenta) {
        List<DetalleVenta> detalles = ventaService.obtenerDetallesDeVenta(idVenta);
        return ResponseEntity.ok(detalles);
    }
}
