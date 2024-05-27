package com.mvc.web.Repository;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Patters.conexionBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IRepositoryUsuarioImpl implements IRepositoryUsuario {

    @Override
    public List<Usuario> Listar() {
        String SQL = "SELECT dni, nombres, apellidos, numTelefono, correo, rol, contrasena FROM Usuarios";

        List<Usuario> usuarios = new ArrayList<Usuario>();

        try (
                Connection con = conexionBD.getConexion();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
        ) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setDni(rs.getString(1));
                usuario.setNombres(rs.getString(2));
                usuario.setApellidos(rs.getString(3));
                usuario.setNumTelefono(rs.getString(4));
                usuario.setCorreo(rs.getString(5));
                usuario.setRol(rs.getString(6));
                usuario.setContrasena(rs.getString(7));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario ListarPorDni(String id) {
        String SQL = "SELECT dni, nombres, apellidos, numTelefono, correo, rol FROM Usuarios WHERE dni = ?";
        Usuario usuario = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setDni(rs.getString(1));
                usuario.setNombres(rs.getString(2));
                usuario.setApellidos(rs.getString(3));
                usuario.setNumTelefono(rs.getString(4));
                usuario.setCorreo(rs.getString(5));
                usuario.setRol(rs.getString(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public int Crear(Usuario usuario) {
        String SQL = "INSERT INTO Usuarios (dni, nombres, apellidos, numTelefono, correo, rol, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {

            pstmt.setString(1, usuario.getDni());
            pstmt.setString(2, usuario.getNombres());
            pstmt.setString(3, usuario.getApellidos());
            pstmt.setString(4, usuario.getNumTelefono());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getRol());
            pstmt.setString(7, usuario.getContrasena());

            resultado = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }

    @Override
    public int Modificar(Usuario objeto) {
        String SQL = "UPDATE usuarios SET contrasena = ? WHERE dni = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, objeto.getContrasena());
            pstmt.setString(2, objeto.getDni());

            resultado = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }

    @Override
    public int Eliminar(String dni) {
        String SQL = "DELETE FROM Usuarios WHERE dni = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {

            pstmt.setString(1, dni);
            resultado = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }

    @Override
    public Usuario findByCorreoAndContrasena(String correo, String contrasena) {
        String SQL = "SELECT dni, nombres, apellidos, numTelefono, rol FROM Usuarios WHERE correo = ? AND contrasena = ?";
        Usuario usuario = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setDni(rs.getString(1));
                usuario.setNombres(rs.getString(2));
                usuario.setApellidos(rs.getString(3));
                usuario.setNumTelefono(rs.getString(4));
                usuario.setRol(rs.getString(5));
                // No establecemos el correo y la contraseña aquí, ya que no los necesitamos y sería una buena práctica evitar devolver la contraseña desde la base de datos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

}
