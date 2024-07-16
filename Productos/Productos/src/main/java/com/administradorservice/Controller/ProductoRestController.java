package com.administradorservice.Controller;

import com.administradorservice.Model.Producto;
import com.administradorservice.Model.ProductoActualizacion;
import com.administradorservice.Repository.IRepositoryProductoImpl;
import com.administradorservice.Services.IServiceProducto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/productos")
public class ProductoRestController {

    @Autowired
    private IServiceProducto serviceProducto;

    @GetMapping("/listarproducto")
    public List<Producto> ListarProducto() {
        return serviceProducto.ListarProducto();
    }

    @GetMapping("/listarProductoPorId/{id}")
    public ResponseEntity<Producto> listarProductoPorId(@PathVariable int id) {
        Producto producto = serviceProducto.ListarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public int crear(@RequestParam("producto") String productoJson,
                     @RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            System.out.println("Producto recibido: " + producto);
            return serviceProducto.Crear(producto, file);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @PutMapping("/actualizarProducto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto, @PathVariable int id) {
        Producto productoExistente = serviceProducto.ListarProductoPorId(id);
        if (productoExistente == null) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setCatProducto(producto.getCatProducto());
        productoExistente.setPrecioProducto(producto.getPrecioProducto());
        productoExistente.setTallaS(producto.getTallaS());
        productoExistente.setTallaM(producto.getTallaM());
        productoExistente.setTallaL(producto.getTallaL());

        int resultado = serviceProducto.ActualizarProducto(productoExistente);
        if (resultado > 0) {
            return new ResponseEntity<>(Map.of("message", "Producto actualizado con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el producto"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarCantidades")
    public ResponseEntity<?> actualizarCantidades(@RequestBody List<ProductoActualizacion> actualizaciones) {
        try {
            for (ProductoActualizacion actualizacion : actualizaciones) {
                IRepositoryProductoImpl.actualizarCantidad(actualizacion.getIdProducto(), actualizacion.getTalla(), actualizacion.getCantidad());
            }
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cantidades actualizadas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al actualizar cantidades");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        int resultado = serviceProducto.Eliminar(id);
        if (resultado > 0) {
            return new ResponseEntity<>(Map.of("message", "Producto y detalles eliminados con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Error al eliminar el producto y sus detalles"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}