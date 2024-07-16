package com.mvc.web.Repository;

import com.mvc.web.Model.Usuario;
import com.mvc.web.Model.UsuarioDetalle;
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
        String consultaExistencia = "SELECT dni FROM Usuarios WHERE dni = ?";
        String insertarUsuario = "INSERT INTO Usuarios (dni, nombres, apellidos, numTelefono, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, 'usuario')";
        int resultado = -1;
        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmtConsulta = con.prepareStatement(consultaExistencia);
                PreparedStatement pstmtInsertar = con.prepareStatement(insertarUsuario);
        ) {
            pstmtConsulta.setString(1, usuario.getDni());
            ResultSet rs = pstmtConsulta.executeQuery();

            if (rs.next()) {
                resultado = -1;
            } else {
                pstmtInsertar.setString(1, usuario.getDni());
                pstmtInsertar.setString(2, usuario.getNombres());
                pstmtInsertar.setString(3, usuario.getApellidos());
                pstmtInsertar.setString(4, usuario.getNumTelefono());
                pstmtInsertar.setString(5, usuario.getCorreo());
                pstmtInsertar.setString(6, usuario.getContrasena());

                resultado = pstmtInsertar.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }
        return resultado;
    }

    @Override
    public int Modificar(String dni, String contrasenaActual, String nuevaContrasena) {
        String SQL = "UPDATE Usuarios SET contrasena = ? WHERE dni = ? AND contrasena = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, nuevaContrasena);
            pstmt.setString(2, dni);
            pstmt.setString(3, contrasenaActual);

            resultado = pstmt.executeUpdate();

            if (resultado > 0) {
                System.out.println("Contraseña modificada correctamente.");
            } else {
                System.out.println("No se pudo modificar la contraseña. Verifique los datos proporcionados.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }
    @Override
    public int actualizarContrasena(String correo, String dni, String nuevaContrasena) {
        String SQL = "UPDATE Usuarios SET contrasena = ? WHERE correo = ? AND dni = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, nuevaContrasena);
            pstmt.setString(2, correo);
            pstmt.setString(3, dni);

            resultado = pstmt.executeUpdate();

            if (resultado > 0) {
                System.out.println("Contraseña modificada correctamente.");
            } else {
                System.out.println("No se pudo modificar la contraseña. Verifique los datos proporcionados.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }



    @Override
    public int Eliminar(String dni) {
        String deleteDetailsSQL = "DELETE FROM detalleUsuarios WHERE dni = ?";
        String deleteUserSQL = "DELETE FROM Usuarios WHERE dni = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement deleteDetailsStmt = con.prepareStatement(deleteDetailsSQL);
                PreparedStatement deleteUserStmt = con.prepareStatement(deleteUserSQL);
        ) {
            deleteDetailsStmt.setString(1, dni);
            deleteDetailsStmt.executeUpdate();

            deleteUserStmt.setString(1, dni);
            resultado = deleteUserStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }

    @Override
    public Usuario findByCorreoAndContrasena(String correo, String contrasena) {
        String SQL = "SELECT dni, nombres, apellidos, numTelefono, correo, rol FROM Usuarios WHERE correo = ? AND contrasena = ?";
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
                usuario.setDni(rs.getString("dni"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setNumTelefono(rs.getString("numTelefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public Usuario findByCorreo(String correo) {
        String SQL = "SELECT dni, nombres, apellidos, numTelefono, correo, rol, contrasena FROM Usuarios WHERE correo = ?";
        Usuario usuario = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setDni(rs.getString("dni"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setNumTelefono(rs.getString("numTelefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setContrasena(rs.getString("contrasena"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }



    @Override
    public int agregarDireccionYCodigoPostal(String dni, String direccion, String codigoPostal) {
        String consultaExistencia = "SELECT COUNT(*) FROM detalleUsuarios WHERE dni = ?";
        String insertarDetalle = "INSERT INTO detalleUsuarios (dni, direccion, codigoPostal) VALUES (?, ?, ?)";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmtConsulta = con.prepareStatement(consultaExistencia);
                PreparedStatement pstmtInsertar = con.prepareStatement(insertarDetalle);
        ) {
            pstmtConsulta.setString(1, dni);
            ResultSet rs = pstmtConsulta.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count >= 2) {
                resultado = -1;
            } else {
                pstmtInsertar.setString(1, dni);
                pstmtInsertar.setString(2, direccion);
                pstmtInsertar.setString(3, codigoPostal);
                resultado = pstmtInsertar.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }
        return resultado;
    }

    @Override
    public int actualizarDireccionYCodigoPostal(int idDetalleUsuarios, String direccion, String codigoPostal) {
        String SQL = "UPDATE detalleUsuarios SET direccion = ?, codigoPostal = ? WHERE idDetalleUsuarios = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, direccion);
            pstmt.setString(2, codigoPostal);
            pstmt.setInt(3, idDetalleUsuarios);
            resultado = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }
        return resultado;
    }

    @Override
    public List<UsuarioDetalle> listarDetallePorDni(String dni) {
        String SQL = "SELECT idDetalleUsuarios, dni, direccion, codigoPostal FROM detalleUsuarios WHERE dni = ?";
        List<UsuarioDetalle> detalles = new ArrayList<>();

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UsuarioDetalle detalle = new UsuarioDetalle();
                detalle.setIdDetalleUsuarios(rs.getInt("idDetalleUsuarios"));
                detalle.setDni(rs.getString("dni"));
                detalle.setDireccion(rs.getString("direccion"));
                detalle.setCodigoPostal(rs.getString("codigoPostal"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @Override
    public int eliminarDetalle(int idDetalleUsuarios) {
        String SQL = "DELETE FROM detalleUsuarios WHERE idDetalleUsuarios = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setInt(1, idDetalleUsuarios);
            resultado = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }
        return resultado;
    }

}