package com.mvc.web.Services;

import com.mvc.web.Model.UsuarioDetalle;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IServiceUsuario {
    int Crear(UsuarioCreateDTO usuarioCreateDTO);

    List<UsuarioDTO> Listar();

    UsuarioDTO ListarPorDni(String dni);

    int Modificar(String dni, String contrasenaActual, String nuevaContrasena);

    int actualizarContrasena(String correo, String dni, String nuevaContrasena);

    int Eliminar(String dni);

    ResponseEntity<UsuarioDTO> iniciarSesion(String correo, String contrasena);

    int agregarDireccionYCodigoPostal(DetalleUsuarioCreateDTO detalleUsuarioCreateDTO);

    int actualizarDireccionYCodigoPostal(DetalleUsuarioUpdateDTO detalleUsuarioUpdateDTO);

    List<DetalleUsuarioDTO> ListarDetallesPorDni(String dni);

    int eliminarDetalle(int idDetalleUsuarios);
}
