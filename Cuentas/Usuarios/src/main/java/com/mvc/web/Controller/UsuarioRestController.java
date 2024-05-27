package com.mvc.web.Controller;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Services.IServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    @Autowired
    private IServiceUsuario serviceUsuario;

    @GetMapping("/listar")
    public List<Usuario> Listar() {
        return serviceUsuario.Listar();
    }

    @GetMapping("/listarporDni/{dni}")
    public Usuario ListarporDni(@PathVariable String dni) {
        return serviceUsuario.ListarPorDni(dni);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public int crear(@RequestBody Usuario usuario) {
        return serviceUsuario.Crear(usuario);
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario loginRequest) {
        return serviceUsuario.iniciarSesion(loginRequest.getCorreo(), loginRequest.getContrasena());
    }

    @PutMapping("/actualizar/{dni}")
    @ResponseStatus(HttpStatus.CREATED)
    public int modificar(@RequestBody Usuario usuario, @PathVariable String dni) {
        Usuario usuario1 = serviceUsuario.ListarPorDni(dni);
        usuario1.setContrasena(usuario.getContrasena());

        return serviceUsuario.Modificar(usuario1);
    }

    @DeleteMapping("/eliminar/{dni}")
    public int eliminar(@PathVariable String dni) {
        return serviceUsuario.Eliminar(dni);
    }
}