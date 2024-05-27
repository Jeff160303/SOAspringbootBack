package com.mvc.web.Services;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Repository.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario {

    @Autowired
    private IRepositoryUsuario repositoryUsuario;

    @Override
    public int Crear(Usuario usuario) {
        return repositoryUsuario.Crear(usuario);
    }

    @Override
    public List<Usuario> Listar() {
        return repositoryUsuario.Listar();
    }

    @Override
    public Usuario ListarPorDni(String dni) {
        return repositoryUsuario.ListarPorDni(dni);
    }

    @Override
    public int Modificar(Usuario usuario) {
        return repositoryUsuario.Modificar(usuario);
    }

    @Override
    public int Eliminar(String dni) { return repositoryUsuario.Eliminar(dni);}

    @Override
    public ResponseEntity<?> iniciarSesion(String correo, String contrasena) {
        Usuario usuario = repositoryUsuario.findByCorreoAndContrasena(correo, contrasena);
        Map<String, Object> response = new HashMap<>();
        if (usuario != null) {
            String token = generateToken(usuario);

            response.put("message", "Login Success");
            response.put("result", true);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("role", usuario.getRol());
            response.put("data", data);

            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Correo electrónico o contraseña incorrectos");
            response.put("result", false);
            response.put("data", null);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    private String generateToken(Usuario usuario) {
        return "generated_token_example";
    }
}
