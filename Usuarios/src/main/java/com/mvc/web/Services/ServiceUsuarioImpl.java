package com.mvc.web.Services;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Model.UsuarioDetalle;
import com.mvc.web.Repository.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario {

    @Autowired
    private IRepositoryUsuario repositoryUsuario;

    @Override
    public int Crear(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setDni(usuarioCreateDTO.getDni());
        usuario.setNombres(usuarioCreateDTO.getNombres());
        usuario.setApellidos(usuarioCreateDTO.getApellidos());
        usuario.setNumTelefono(usuarioCreateDTO.getNumTelefono());
        usuario.setCorreo(usuarioCreateDTO.getCorreo());
        usuario.setRol(usuarioCreateDTO.getRol());
        usuario.setContrasena(usuarioCreateDTO.getContrasena());

        return repositoryUsuario.Crear(usuario);
    }

    @Override
    public List<UsuarioDTO> Listar() {
        return repositoryUsuario.Listar().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO ListarPorDni(String dni) {
        Usuario usuario = repositoryUsuario.ListarPorDni(dni);
        return convertToDTO(usuario);
    }

    @Override
    public int Modificar(String dni, String contrasenaActual, String nuevaContrasena) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setContrasena(contrasenaActual);
        return repositoryUsuario.Modificar(dni, contrasenaActual, nuevaContrasena);
    }

    @Override
    public int actualizarContrasena(String correo, String dni, String nuevaContrasena) {
        Usuario usuario = repositoryUsuario.findByCorreo(correo);
        if (usuario != null && usuario.getDni().equals(dni)) {
            int resultado = repositoryUsuario.actualizarContrasena(correo, dni, nuevaContrasena);
            if (resultado == 1) {
                return 1; // Indica éxito
            } else {
                return 0; // Indica fallo
            }
        } else {
            return -1; // Indica usuario no encontrado o datos incorrectos
        }
    }


    @Override
    public int Eliminar(String dni) {
        return repositoryUsuario.Eliminar(dni);
    }

    @Override
    public ResponseEntity<UsuarioDTO> iniciarSesion(String correo, String contrasena) {
        Usuario usuario = repositoryUsuario.findByCorreoAndContrasena(correo, contrasena);
        if (usuario != null) {
            return ResponseEntity.ok(convertToDTO(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public int agregarDireccionYCodigoPostal(DetalleUsuarioCreateDTO detalleUsuarioCreateDTO) {
        return repositoryUsuario.agregarDireccionYCodigoPostal(
                detalleUsuarioCreateDTO.getDni(),
                detalleUsuarioCreateDTO.getDireccion(),
                detalleUsuarioCreateDTO.getCodigoPostal()
        );
    }

    @Override
    public int actualizarDireccionYCodigoPostal(DetalleUsuarioUpdateDTO detalleUsuarioUpdateDTO) {
        return repositoryUsuario.actualizarDireccionYCodigoPostal(
                detalleUsuarioUpdateDTO.getIdDetalleUsuarios(),
                detalleUsuarioUpdateDTO.getDireccion(),
                detalleUsuarioUpdateDTO.getCodigoPostal()
        );
    }

    @Override
    public List<DetalleUsuarioDTO> ListarDetallesPorDni(String dni) {
        return repositoryUsuario.listarDetallePorDni(dni).stream()
                .map(this::convertToDetalleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int eliminarDetalle(int idDetalleUsuarios) {
        return repositoryUsuario.eliminarDetalle(idDetalleUsuarios);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setNumTelefono(usuario.getNumTelefono());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setRol(usuario.getRol());
        return usuarioDTO;
    }

    private DetalleUsuarioDTO convertToDetalleDTO(UsuarioDetalle detalleUsuario) {
        DetalleUsuarioDTO detalleDTO = new DetalleUsuarioDTO();
        detalleDTO.setIdDetalleUsuarios(detalleUsuario.getIdDetalleUsuarios());
        detalleDTO.setDni(detalleUsuario.getDni());
        detalleDTO.setDireccion(detalleUsuario.getDireccion());
        detalleDTO.setCodigoPostal(detalleUsuario.getCodigoPostal());
        return detalleDTO;
    }
}
