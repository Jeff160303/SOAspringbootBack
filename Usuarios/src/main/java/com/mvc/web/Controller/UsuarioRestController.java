package com.mvc.web.Controller;

import com.mvc.web.Services.UsuarioDTO;
import com.mvc.web.Services.UsuarioCreateDTO;
import com.mvc.web.Services.UsuarioUpdatePasswordDTO;
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
    public List<UsuarioDTO> Listar() {
        return serviceUsuario.Listar();
    }

    @GetMapping("/listarporDni/{dni}")
    public UsuarioDTO ListarporDni(@PathVariable String dni) {
        return serviceUsuario.ListarPorDni(dni);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public int crear(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        return serviceUsuario.Crear(usuarioCreateDTO);
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody UsuarioCreateDTO loginRequest) {
        return serviceUsuario.iniciarSesion(loginRequest.getCorreo(), loginRequest.getContrasena());
    }

    @PutMapping("/actualizar")
    @ResponseStatus(HttpStatus.CREATED)
    public int modificar(@RequestBody UsuarioUpdatePasswordDTO usuarioUpdatePasswordDTO) {
        return serviceUsuario.Modificar(usuarioUpdatePasswordDTO);
    }

    @DeleteMapping("/eliminar/{dni}")
    public int eliminar(@PathVariable String dni) {
        return serviceUsuario.Eliminar(dni);
    }
}
