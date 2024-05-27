package com.administradorservice.Controller;

import com.administradorservice.Model.DetalleProducto;
import com.administradorservice.Model.Producto;
import com.administradorservice.Services.IServiceProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/productos")
public class ProductoRestController {

    @Autowired
    private IServiceProducto serviceProducto;

    @GetMapping("/listar") //lista los productos en la pagina que vera el cliente (mustra el nombre y precio del producto.)
    public List<Producto> Listar() {
        return serviceProducto.Listar();
    }

    @GetMapping("/listarproducto") //lista solo la tabla producto, necesario para mostrar los prodcutos cuando se tiene que modificar o eliminar.
    public List<Producto> ListarProducto() {
        return serviceProducto.ListarProducto();
    }

    @GetMapping("/listardetalleproducto") //lista solo la tabla detalleProducto - NO SE USA DE MOMENTO
    public List<DetalleProducto> ListarDetallesProducto() {
        return serviceProducto.ListarDetallesProducto();
    }

    @GetMapping("/listarDetalleProductoPorIdProducto/{id}")
    public List<DetalleProducto> listarDetalleProductoPorIdProducto(@PathVariable int id) {
        Producto producto = serviceProducto.listarDetalleProductoPorIdProducto(id);
        if (producto != null) {
            return producto.getDetalles();
        } else {
            return Collections.emptyList(); // Retorna una lista vacía si el producto no se encuentra
        }
    }

    @GetMapping("/listarProductoPorId/{id}") //lista la tabla prodcuto con un el idProducto
    public Producto ListarProductoPorId(@PathVariable int id) {
        return serviceProducto.ListarProductoPorId(id);
    }

    @GetMapping("/listarDetalleProductoPorId/{id}") //lista la tabla detalle producto con el idDetalleProducto
    public DetalleProducto ListarDetalleProductoPorId(@PathVariable int id) {
        return serviceProducto.ListarDetalleProductoPorId(id);
    }

    @PostMapping("/crear") //Se usa para crear el producto con su detalle.
    @ResponseStatus(HttpStatus.CREATED)
    public int crear(@RequestBody Producto producto) {
        System.out.println("Producto recibido: " + producto);
        return serviceProducto.Crear(producto);
    }

    @PostMapping("/upload") //Se usa para subir la imagen al servidor.
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get("C:/Users/jeffe/Desktop/pruebas/Productos/Productos/src/main/resources/static/imagenes/" + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Failed to upload file"));
        }
        Map<String, String> response = new HashMap<>();
        response.put("imageName", fileName);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizarProducto/{id}") //Se usa para actualizar solo el producto a traves de su id
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto, @PathVariable int id) {
        Producto productoExistente = serviceProducto.ListarProductoPorId(id);
        if (productoExistente == null) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setCatProducto(producto.getCatProducto());
        productoExistente.setPrecioProducto(producto.getPrecioProducto());

        int resultado = serviceProducto.ActualizarProducto(productoExistente);
        if (resultado > 0) {
            return new ResponseEntity<>(Map.of("message", "Producto actualizado con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar el producto"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarDetalles/{idProducto}") //se usa para actualizar el detalle a traves del id del producto.
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> actualizarDetallesProducto(@RequestBody List<DetalleProducto> detalles, @PathVariable int idProducto) {
        int resultado = serviceProducto.ActualizarDetallesProducto(idProducto, detalles);
        if (resultado > 0) {
            return new ResponseEntity<>(Map.of("message", "Detalles actualizados con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar los detalles"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }









    @DeleteMapping("/eliminarProductoyDetalle/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        int resultado = serviceProducto.Eliminar(id);
        if (resultado > 0) {
            return new ResponseEntity<>(Map.of("message", "Producto y detalles eliminados con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Error al eliminar el producto y sus detalles"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}