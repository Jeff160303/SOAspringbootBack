package com.deliveryservice;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    public List<Venta> listarTodasLasVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Venta";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("idVenta"));
                venta.setFechaEmision(rs.getTimestamp("fechaEmision").toLocalDateTime());
                venta.setDni(rs.getString("dni"));
                venta.setNombres(rs.getString("nombres"));
                venta.setApellidos(rs.getString("apellidos"));
                venta.setTipoVenta(rs.getString("tipoVenta"));
                venta.setDireccion(rs.getString("direccion"));
                venta.setTotal(rs.getBigDecimal("total"));
                venta.setEstado(rs.getString("estado"));
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar todas las ventas", e);
        }
        return ventas;
    }

    public List<Venta> listarVentasPorDni(String dni) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Venta WHERE dni = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("idVenta"));
                    venta.setFechaEmision(rs.getTimestamp("fechaEmision").toLocalDateTime());
                    venta.setDni(rs.getString("dni"));
                    venta.setNombres(rs.getString("nombres"));
                    venta.setApellidos(rs.getString("apellidos"));
                    venta.setTipoVenta(rs.getString("tipoVenta"));
                    venta.setDireccion(rs.getString("direccion"));
                    venta.setTotal(rs.getBigDecimal("total"));
                    venta.setEstado(rs.getString("estado"));
                    ventas.add(venta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar las ventas por DNI", e);
        }
        return ventas;
    }

    public Venta actualizarEstado(int idVenta, String nuevoEstado) {
        String sqlUpdate = "UPDATE Venta SET estado = ? WHERE idVenta = ?";
        String sqlSelect = "SELECT * FROM Venta WHERE idVenta = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
             PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {

            psUpdate.setString(1, nuevoEstado);
            psUpdate.setInt(2, idVenta);
            psUpdate.executeUpdate();

            psSelect.setInt(1, idVenta);

            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("idVenta"));
                    venta.setFechaEmision(rs.getTimestamp("fechaEmision").toLocalDateTime());
                    venta.setDni(rs.getString("dni"));
                    venta.setNombres(rs.getString("nombres"));
                    venta.setApellidos(rs.getString("apellidos"));
                    venta.setTipoVenta(rs.getString("tipoVenta"));
                    venta.setDireccion(rs.getString("direccion"));
                    venta.setTotal(rs.getBigDecimal("total"));
                    venta.setEstado(rs.getString("estado"));
                    return venta;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el estado de la venta", e);
        }
        throw new RuntimeException("Venta no encontrada");
    }

    public List<DetalleVenta> obtenerDetallesDeVenta(int idVenta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetalleVenta WHERE idVenta = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
                    detalle.setIdVenta(rs.getInt("idVenta"));
                    detalle.setNombreProducto(rs.getString("nombreProducto"));
                    detalle.setCantProducto(rs.getInt("cantProducto"));
                    detalle.setTallaProducto(rs.getString("tallaProducto"));
                    detalle.setPrecioProducto(rs.getBigDecimal("precioProducto"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener los detalles de la venta", e);
        }
        return detalles;
    }
}
