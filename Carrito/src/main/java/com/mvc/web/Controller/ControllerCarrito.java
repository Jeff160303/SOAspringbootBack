package com.mvc.web.Controller;

import com.mvc.web.Model.Carrito;
import com.mvc.web.Services.IServiceCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/carrito")
public class ControllerCarrito {

    @Autowired
    private IServiceCarrito serviceCarrito;

    @GetMapping("/listar/{dni}")
    public List<Carrito> listarPorDni(@PathVariable String dni) {
        return serviceCarrito.ListarPorDni(dni);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public int crear(@RequestBody Carrito carrito) {
        return serviceCarrito.Crear(carrito);
    }

    @PutMapping("/actualizar/{id_carrito}")
    @ResponseStatus(HttpStatus.OK)
    public int modificar(@RequestBody Carrito carrito, @PathVariable("id_carrito") int id_carrito) {
        Carrito carritoExistente = serviceCarrito.ListarPorId(id_carrito);
        if (carritoExistente != null) {
            carritoExistente.setTalla(carrito.getTalla());
            carritoExistente.setCantidadProducto(carrito.getCantidadProducto());
            return serviceCarrito.Modificar(carritoExistente);
        } else {
            return 0;
        }
    }

    @DeleteMapping("/eliminar/{id_carrito}")
    public int eliminar(@PathVariable int id_carrito) {
        return serviceCarrito.Eliminar(id_carrito);
    }
}
