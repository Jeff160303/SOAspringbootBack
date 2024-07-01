package com.mvc.web.Repository;

import com.mvc.web.Model.Usuario;
import java.util.List;

public interface IRepositoryUsuario {
    List<Usuario> Listar();
    Usuario ListarPorDni(String dni);
    int Crear(Usuario usuario);
    int Modificar(Usuario usuario);
    int Eliminar(String dni);
    Usuario findByCorreoAndContrasena(String correo, String contrasena);
}
