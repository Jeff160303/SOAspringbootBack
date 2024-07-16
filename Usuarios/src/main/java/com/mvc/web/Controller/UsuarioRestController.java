package com.mvc.web.Controller;

import com.mvc.web.Services.*;
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
        String dni = usuarioUpdatePasswordDTO.getDni();
        String contrasenaActual = usuarioUpdatePasswordDTO.getContrasenaActual();
        String nuevaContrasena = usuarioUpdatePasswordDTO.getNuevaContrasena();

        return serviceUsuario.Modificar(dni, contrasenaActual, nuevaContrasena);
    }

    @PutMapping("/olvidoContraseña/{correo}/{dni}")
    public ResponseEntity<String> actualizarContrasena(@PathVariable String correo, @PathVariable String dni, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        int result = serviceUsuario.actualizarContrasena(correo, dni, updatePasswordDTO.getNewPassword());
        if (result == 1) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
    }



    @DeleteMapping("/eliminar/{dni}")
    public int eliminar(@PathVariable String dni) {
        return serviceUsuario.Eliminar(dni);
    }

    @PostMapping("/detalle/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public int agregarDetalle(@RequestBody DetalleUsuarioCreateDTO detalleUsuarioCreateDTO) {
        return serviceUsuario.agregarDireccionYCodigoPostal(detalleUsuarioCreateDTO);
    }

    @PutMapping("/detalle/actualizar")
    @ResponseStatus(HttpStatus.CREATED)
    public int actualizarDetalle(@RequestBody DetalleUsuarioUpdateDTO detalleUsuarioUpdateDTO) {
        return serviceUsuario.actualizarDireccionYCodigoPostal(detalleUsuarioUpdateDTO);
    }

    @GetMapping("/detalle/listar/{dni}")
    public List<DetalleUsuarioDTO> ListarDetallesPorDni(@PathVariable String dni) {
        return serviceUsuario.ListarDetallesPorDni(dni);
    }

    @DeleteMapping("/detalle/eliminar/{idDetalleUsuarios}")
    public int eliminarDetalle(@PathVariable int idDetalleUsuarios) {
        return serviceUsuario.eliminarDetalle(idDetalleUsuarios);
    }
}
