package com.mvc.web.Repository;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Model.UsuarioDetalle;
import com.mvc.web.Services.DetalleUsuarioDTO;

import java.util.List;

public interface IRepositoryUsuario {
    List<Usuario> Listar();

    Usuario ListarPorDni(String dni);

    int Crear(Usuario usuario);

    int Modificar(String dni, String contrasenaActual, String nuevaContrasena);

    int actualizarContrasena(String correo, String dni, String nuevaContrasena);

    Usuario findByCorreo(String correo);

    int Eliminar(String dni);

    Usuario findByCorreoAndContrasena(String correo, String contrasena);

    int agregarDireccionYCodigoPostal(String dni, String direccion, String codigoPostal);

    int actualizarDireccionYCodigoPostal(int idDetalleUsuarios, String direccion, String codigoPostal);

    List<UsuarioDetalle> listarDetallePorDni(String dni);

    int eliminarDetalle(int idDetalleUsuarios);
}
