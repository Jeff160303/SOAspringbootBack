package com.mvc.web.Repository;

import com.mvc.web.Model.Usuario;

import java.util.List;

public interface IRepositoryUsuario {

    List<Usuario> Listar();
    Usuario ListarPorDni(String Dni);
    int Crear(Usuario objeto);
    int Modificar(Usuario objeto);
    int Eliminar(String Dni);
    Usuario findByCorreoAndContrasena(String correo, String contrasena);
}

