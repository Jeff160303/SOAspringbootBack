package com.mvc.web.Services;

import com.mvc.web.Model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IServiceUsuario {

    int Crear(Usuario usuario);
    List<Usuario> Listar();
    Usuario ListarPorDni(String dni);
    int Modificar(Usuario usuario);
    int Eliminar(String dni);
    ResponseEntity<?> iniciarSesion(String correo, String contrasena);
}
