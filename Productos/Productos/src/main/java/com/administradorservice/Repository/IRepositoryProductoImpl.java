package com.administradorservice.Repository;

import com.administradorservice.Model.Producto;
import com.administradorservice.Patters.conexionBD;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IRepositoryProductoImpl implements IRepositoryProducto{

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
                producto.setIdProducto(rs.getInt("idproducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setCatProducto(rs.getString("catProducto"));
                producto.setPrecioProducto(rs.getDouble("precioProducto"));
                producto.setTallaS(rs.getInt("tallaS"));
                producto.setTallaM(rs.getInt("tallaM"));
                producto.setTallaL(rs.getInt("tallaL"));
                producto.setImagenProducto(rs.getString("imgProducto"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return productos;
    }



    private String uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get("C:/Users/jeffe/Desktop/Spring Boot/Productos/Productos/src/main/resources/static/imagenes/" + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public int Crear(Producto producto, MultipartFile file) {
        String SQLProducto = "INSERT INTO Producto (nombreProducto, catProducto, precioProducto, tallaS, tallaM, tallaL, imgProducto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int resultadoProducto = -1;

        try {
            // Subir el archivo y obtener el nombre del archivo
            String fileName = uploadFile(file);
            producto.setImagenProducto(fileName);

            try (
                    Connection con = conexionBD.getConexion();
                    PreparedStatement pstmtProducto = con.prepareStatement(SQLProducto, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmtProducto.setString(1, producto.getNombreProducto());
                pstmtProducto.setString(2, producto.getCatProducto());
                pstmtProducto.setDouble(3, producto.getPrecioProducto());
                pstmtProducto.setInt(4, producto.getTallaS());
                pstmtProducto.setInt(5, producto.getTallaM());
                pstmtProducto.setInt(6, producto.getTallaL());
                pstmtProducto.setString(7, producto.getImagenProducto());
                resultadoProducto = pstmtProducto.executeUpdate();

                if (resultadoProducto > 0) {
                    System.out.println("Producto insertado correctamente en la base de datos.");
                } else {
                    System.out.println("No se pudo insertar el producto en la base de datos.");
                }
            } catch (SQLException e) {
                System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Error al subir el archivo: " + e.getMessage());
            e.printStackTrace();
        }

        return resultadoProducto;
    }



    @Override
    public Producto ListarProductoPorId(int id) {
        String SQLProducto = "SELECT * FROM Producto WHERE idProducto = ?";
        Producto producto = null;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmtProducto = con.prepareStatement(SQLProducto);
        ) {
            pstmtProducto.setInt(1, id);
            ResultSet rs = pstmtProducto.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setCatProducto(rs.getString("catProducto"));
                producto.setPrecioProducto(rs.getDouble("precioProducto"));
                producto.setTallaS(rs.getInt("tallaS"));
                producto.setTallaM(rs.getInt("tallaM"));
                producto.setTallaL(rs.getInt("tallaL"));
                producto.setImagenProducto(rs.getString("imgProducto"));
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public int ActualizarProducto(Producto producto) {
        String SQLProducto = "UPDATE Producto SET nombreProducto = ?, catProducto = ?, precioProducto = ?, tallaS = ?, tallaM = ?, tallaL = ? WHERE idProducto = ?";
        int resultado = -1;

        try (
                Connection con = conexionBD.getConexion();
                PreparedStatement pstmtProducto = con.prepareStatement(SQLProducto);
        ) {
            pstmtProducto.setString(1, producto.getNombreProducto());
            pstmtProducto.setString(2, producto.getCatProducto());
            pstmtProducto.setDouble(3, producto.getPrecioProducto());
            pstmtProducto.setInt(4, producto.getTallaS());
            pstmtProducto.setInt(5, producto.getTallaM());
            pstmtProducto.setInt(6, producto.getTallaL());
            pstmtProducto.setInt(7, producto.getIdProducto());

            resultado = pstmtProducto.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }



    @Override
    public int Eliminar(int id) {
        String SQL_DELETE_PRODUCT = "DELETE FROM Producto WHERE idproducto = ?";
        int resultado = -1;
        Connection con = null;

        try {
            con = conexionBD.getConexion();
            PreparedStatement pstmtDeleteProduct = con.prepareStatement(SQL_DELETE_PRODUCT);
            con.setAutoCommit(false);

            pstmtDeleteProduct.setInt(1, id);
            resultado = pstmtDeleteProduct.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
            if (con != null) {
                try {
                    con.rollback();
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
