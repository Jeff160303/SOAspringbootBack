package com.mvc.web.Services;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Services.UsuarioDTO;
import com.mvc.web.Services.UsuarioCreateDTO;
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
    public int Modificar(UsuarioUpdatePasswordDTO usuarioUpdatePasswordDTO) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(usuarioUpdatePasswordDTO.getCorreo());
        usuario.setContrasena(usuarioUpdatePasswordDTO.getContrasena());
        return repositoryUsuario.Modificar(usuario);
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
}
