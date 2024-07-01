package com.mvc.web.Repository;

import com.mvc.web.Model.Carrito;
import com.mvc.web.Patters.conexionBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class IRepositoryCarritoImpl implements IRepositoryCarrito {

    @Override
    public List<Carrito> ListarPorDni(String dni) {
        String SQL = "SELECT idCarrito, idProducto, talla, cantidadProducto FROM Carrito WHERE dni = ?";

        List<Carrito> carritos = new ArrayList<>();

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Carrito carrito = new Carrito();
                    carrito.setIdCarrito(rs.getInt("idCarrito"));
                    carrito.setIdProducto(rs.getInt("idProducto"));
                    carrito.setTalla(rs.getString("talla"));
                    carrito.setCantidadProducto(rs.getInt("cantidadProducto"));

                    carritos.add(carrito);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carritos;
    }



    @Override
    public Carrito ListarPorId(int idCarrito) {
        String SQL = "SELECT idCarrito, idProducto, dni, talla, cantidadProducto FROM Carrito WHERE idCarrito = ?";
        Carrito carrito = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setInt(1, idCarrito);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    carrito = new Carrito();
                    carrito.setIdCarrito(rs.getInt("idCarrito"));
                    carrito.setIdProducto(rs.getInt("idProducto"));
                    carrito.setDni(rs.getString("dni"));
                    carrito.setTalla(rs.getString("talla"));
                    carrito.setCantidadProducto(rs.getInt("cantidadProducto"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carrito;
    }




    @Override
    public int Crear(Carrito carrito) {
        String SQL = "INSERT INTO Carrito (idProducto, dni, talla, cantidadProducto) VALUES (?, ?, ?, ?)";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setInt(1, carrito.getIdProducto());
            pstmt.setString(2, carrito.getDni());
            pstmt.setString(3, carrito.getTalla());
            pstmt.setInt(4, carrito.getCantidadProducto());

            resultado = pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carrito.setIdCarrito(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }


    @Override
    public int Modificar(Carrito objeto) {
        String SQL = "UPDATE Carrito SET talla = ?, cantidadProducto = ? WHERE idCarrito = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setString(1, objeto.getTalla());
            pstmt.setInt(2, objeto.getCantidadProducto());
            pstmt.setInt(3, objeto.getIdCarrito());

            resultado = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }


    @Override
    public int Eliminar(int idCarrito) {
        String SQL = "DELETE FROM Carrito WHERE idCarrito = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setInt(1, idCarrito);
            resultado = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }

        return resultado;
    }


}
