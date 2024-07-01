package com.mvc.web.Services;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IServiceUsuario {
    int Crear(UsuarioCreateDTO usuarioCreateDTO);

    List<UsuarioDTO> Listar();

    UsuarioDTO ListarPorDni(String dni);

    int Modificar(UsuarioUpdatePasswordDTO usuarioUpdatePasswordDTO);

    int Eliminar(String dni);

    ResponseEntity<UsuarioDTO> iniciarSesion(String correo, String contrasena);
}
