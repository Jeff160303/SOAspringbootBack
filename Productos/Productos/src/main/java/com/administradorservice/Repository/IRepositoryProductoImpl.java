package com.administradorservice.Repository;


import com.administradorservice.Model.DetalleProducto;
import com.administradorservice.Model.Producto;
import com.administradorservice.Patters.conexionBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IRepositoryProductoImpl implements IRepositoryProducto{

    @Override
    public List<Producto> Listar() {
        String SQL = "SELECT p.idproducto, p.nombreProducto, p.catProducto, p.precioProducto, " +
                "dp.idDetallePro, dp.tallas, dp.stock, dp.imagenProducto " +
                "FROM Producto p " +
                "LEFT JOIN DetalleProducto dp ON p.idproducto = dp.idproducto";

        List<Producto> productos = new ArrayList<>();

        try (
                Connection con = conexionBD.getConexion();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
        ) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setCatProducto(rs.getString("catProducto"));
                producto.setPrecioProducto(rs.getDouble("precioProducto"));

                // Verificar si hay un DetalleProducto asociado
                if (rs.getObject("idDetallePro") != null) {
                    DetalleProducto detalleProducto = new DetalleProducto();
                    detalleProducto.setIdDetallePro(rs.getInt("idDetallePro"));
                    detalleProducto.setIdproducto(rs.getInt("idproducto"));
                    detalleProducto.setTallas(rs.getString("tallas"));
                    detalleProducto.setStock(rs.getInt("stock"));
                    detalleProducto.setImagenProducto(rs.getString("imagenProducto"));

                    producto.setDetalleProducto(detalleProducto);
                }

                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    @Override
    public List<Producto> ListarProducto() {
        String SQL = "SELECT * FROM Producto";

        List<Producto> productos = new ArrayList<>();

        try (
                Connection con = conexionBD.getConexion();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
        ) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setCatProducto(rs.getString("catProducto"));
                producto.setPrecioProducto(rs.getDouble("precioProducto"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    @Override
    public List<DetalleProducto> ListarDetallesProducto() {
        String SQL = "SELECT * FROM DetalleProducto"; // Consulta para seleccionar todos los campos de la tabla DetalleProducto

        List<DetalleProducto> detalles = new ArrayList<>();

        try (
                Connection con = conexionBD.getConexion();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
        ) {
            while (rs.next()) {
                DetalleProducto detalle = new DetalleProducto();
                detalle.setIdDetallePro(rs.getInt("idDetallePro"));
                detalle.setIdproducto(rs.getInt("idproducto"));
                detalle.setTallas(rs.getString("tallas"));
                detalle.setStock(rs.getInt("stock"));
                detalle.setImagenProducto(rs.getString("imagenProducto"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }


    @Override
    public Producto listarDetalleProductoPorIdProducto(int id) {
        Producto producto = new Producto();
        producto.setIdproducto(id); // Establece el idproducto del producto

        try {
            Connection con = conexionBD.getConexion(); // Obtén la conexión a la base de datos
            String sql = "SELECT * FROM DetalleProducto WHERE idproducto = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            List<DetalleProducto> detalles = new ArrayList<>();
            while (rs.next()) {
                DetalleProducto detalle = new DetalleProducto();
                detalle.setIdDetallePro(rs.getInt("idDetallePro"));
                detalle.setIdproducto(id); // Establece el idproducto en cada detalle
                detalle.setTallas(rs.getString("tallas"));
                detalle.setStock(rs.getInt("stock"));
                detalle.setImagenProducto(rs.getString("imagenProducto"));
                detalles.add(detalle);
            }
            producto.setDetalles(detalles);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }


    @Override
    public Producto ListarProductoPorId(int id) {
        String SQL = "SELECT * FROM Producto WHERE idproducto = ?";
        Producto producto = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setCatProducto(rs.getString("catProducto"));
                producto.setPrecioProducto(rs.getDouble("precioProducto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public DetalleProducto ListarDetalleProductoPorId(int id) {
        String SQL = "SELECT * FROM DetalleProducto WHERE idDetallePro = ?";
        DetalleProducto detalle = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmt = con.prepareStatement(SQL);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                detalle = new DetalleProducto();
                detalle.setIdDetallePro(rs.getInt("idDetallePro"));
                detalle.setIdproducto(rs.getInt("idproducto"));
                detalle.setTallas(rs.getString("tallas"));
                detalle.setStock(rs.getInt("stock"));
                detalle.setImagenProducto(rs.getString("imagenProducto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalle;
    }




    @Override
    public int Crear(Producto producto) {
        String SQLProducto = "INSERT INTO Producto (nombreProducto, catProducto, precioProducto) VALUES (?, ?, ?)";
        String SQLDetalle = "INSERT INTO DetalleProducto (idproducto, tallas, stock, imagenProducto) VALUES (?, ?, ?, ?)";
        int resultadoProducto = -1;
        int resultadoDetalle = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmtProducto = con.prepareStatement(SQLProducto, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtDetalle = con.prepareStatement(SQLDetalle);
        ) {
            // Insertar datos del producto
            pstmtProducto.setString(1, producto.getNombreProducto());
            pstmtProducto.setString(2, producto.getCatProducto());
            pstmtProducto.setDouble(3, producto.getPrecioProducto());
            resultadoProducto = pstmtProducto.executeUpdate();

            if (resultadoProducto > 0) {
                // Obtener el ID generado para el producto
                ResultSet generatedKeys = pstmtProducto.getGeneratedKeys();
                int idProducto = -1;
                if (generatedKeys.next()) {
                    idProducto = generatedKeys.getInt(1);
                }

                // Insertar detalles del producto para cada talla seleccionada
                for (DetalleProducto detalle : producto.getDetalles()) {
                    pstmtDetalle.setInt(1, idProducto);
                    pstmtDetalle.setString(2, detalle.getTallas());
                    pstmtDetalle.setInt(3, detalle.getStock());
                    pstmtDetalle.setString(4, detalle.getImagenProducto());
                    resultadoDetalle = pstmtDetalle.executeUpdate();

                    if (resultadoDetalle <= 0) {
                        break; // Si falla la inserción de algún detalle, salir del bucle
                    }
                }

                // Comprobar si ambos insertos se realizaron correctamente
                if (resultadoProducto > 0 && resultadoDetalle > 0) {
                    return 1; // Éxito
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return -1; // Fallo
    }





    public int ActualizarProducto(Producto producto) {
        try {
            Connection con = conexionBD.getConexion(); // Obtén la conexión a la base de datos
            String sql = "UPDATE Producto SET nombreProducto = ?, catProducto = ?, precioProducto = ? WHERE idproducto = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, producto.getNombreProducto());
            stmt.setString(2, producto.getCatProducto());
            stmt.setDouble(3, producto.getPrecioProducto());
            stmt.setInt(4, producto.getIdproducto()); // Asume que tienes un método getIdProducto() en la clase Producto
            int filasActualizadas = stmt.executeUpdate();
            con.close();
            return filasActualizadas;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Manejo de errores
        }
    }

    public int ActualizarDetallesProducto(int idProducto, List<DetalleProducto> detalles) {
        try {
            Connection con = conexionBD.getConexion();
            String sql = "UPDATE DetalleProducto SET tallas = ?, stock = ?, imagenProducto = ? WHERE idproducto = ? AND idDetallePro = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            for (DetalleProducto detalle : detalles) {
                stmt.setString(1, detalle.getTallas());
                stmt.setInt(2, detalle.getStock());
                stmt.setString(3, detalle.getImagenProducto());
                stmt.setInt(4, idProducto);
                stmt.setInt(5, detalle.getIdDetallePro());
                stmt.addBatch();
            }
            int[] filasActualizadas = stmt.executeBatch();
            con.close();
            return filasActualizadas.length;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Manejo de errores
        }
    }









    @Override
    public int Eliminar(int id) {
        String SQL_DELETE_DETAILS = "DELETE FROM DetalleProducto WHERE idproducto = ?";
        String SQL_DELETE_PRODUCT = "DELETE FROM Producto WHERE idproducto = ?";
        int resultado = -1;
        Connection con = null;

        try {
            con = conexionBD.getConexion();
            PreparedStatement pstmtDeleteDetails = con.prepareStatement(SQL_DELETE_DETAILS);
            PreparedStatement pstmtDeleteProduct = con.prepareStatement(SQL_DELETE_PRODUCT);

            // Iniciar la transacción
            con.setAutoCommit(false);

            // Eliminar detalles del producto
            pstmtDeleteDetails.setInt(1, id);
            pstmtDeleteDetails.executeUpdate();

            // Eliminar producto
            pstmtDeleteProduct.setInt(1, id);
            resultado = pstmtDeleteProduct.executeUpdate();

            // Confirmar la transacción
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
            if (con != null) {
                try {
                    con.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultado;
    }



}
