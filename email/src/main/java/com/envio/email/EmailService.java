package com.envio.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeMessage(String to, String name) {
        String subject = "Bienvenido a nuestra tienda";
        String htmlContent = String.format(
                "<div style='text-align: center;'>"
                        + "<h1>Bienvenido %s a nuestra tienda</h1>"
                        + "<p>¡Estamos encantados de tenerte con nosotros! Disfruta de un <strong>30%% de descuento</strong> en todas nuestras prendas de hombre y de mujer.</p>"
                        + "<p>Visítanos pronto y aprovecha esta oferta exclusiva.</p>"
                        + "<p>Saludos,<br>Tu tienda favorita</p>"
                        + "<a href='http://localhost:4200/' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #007BFF; text-decoration: none; border-radius: 5px;'>Visítanos</a>"
                        + "</div>",
                name);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }



    public void sendBoleta(String to, Venta venta, List<DetalleVenta> detalles) {
        String subject = "Boleta de Compra";
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div style='text-align: center;'>")
                .append("<h1>Boleta de Compra</h1>")
                .append("<p>Gracias por tu compra. Aquí están los detalles de tu boleta:</p>")
                .append("<p>Fecha de Emisión: ").append(venta.getFechaEmision()).append("</p>")
                .append("<p>Nombre: ").append(venta.getNombres()).append(" ").append(venta.getApellidos()).append("</p>")
                .append("<p>DNI: ").append(venta.getDni()).append("</p>")
                .append("<p>Tipo de Venta: ").append(venta.getTipoVenta()).append("</p>")
                .append("<p>Dirección: ").append(venta.getDireccion()).append("</p>")
                .append("<p>Total: ").append(venta.getTotal()).append("</p>")
                .append("<p>Estado: ").append(venta.getEstado()).append("</p>")
                .append("<h2>Detalles de los Productos</h2>")
                .append("<table style='width: 100%; border: 1px solid black;'>")
                .append("<tr><th>Producto</th><th>Cantidad</th><th>Talla</th><th>Precio</th></tr>");

        for (DetalleVenta detalle : detalles) {
            htmlContent.append("<tr>")
                    .append("<td>").append(detalle.getNombreProducto()).append("</td>")
                    .append("<td>").append(detalle.getCantProducto()).append("</td>")
                    .append("<td>").append(detalle.getTallaProducto()).append("</td>")
                    .append("<td>").append(detalle.getPrecioProducto()).append("</td>")
                    .append("</tr>");
        }

        htmlContent.append("</table>")
                .append("<p>Saludos,<br>Tu tienda favorita</p>")
                .append("<a href='http://localhost:4200/' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #007BFF; text-decoration: none; border-radius: 5px;'>Visítanos</a>")
                .append("</div>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar la boleta por correo electrónico", e);
        }
    }




    public void guardarDatosDelCarrito(Venta venta, List<DetalleVenta> detalles) {
        Connection con = null;
        PreparedStatement ventaStmt = null;
        PreparedStatement detalleStmt = null;

        try {
            con = conexionBD.getConexion();
            con.setAutoCommit(false);

            // Insertar venta
            String ventaSQL = "INSERT INTO Venta (fechaEmision, dni, nombres, apellidos, tipoVenta, direccion, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ventaStmt = con.prepareStatement(ventaSQL, new String[]{"idVenta"});
            ventaStmt.setObject(1, venta.getFechaEmision());
            ventaStmt.setString(2, venta.getDni());
            ventaStmt.setString(3, venta.getNombres());
            ventaStmt.setString(4, venta.getApellidos());
            ventaStmt.setString(5, venta.getTipoVenta());
            ventaStmt.setString(6, venta.getDireccion());
            ventaStmt.setBigDecimal(7, venta.getTotal());
            ventaStmt.setString(8, venta.getEstado());
            ventaStmt.executeUpdate();

            // Obtener el id de la venta insertada
            int idVenta = 0;
            try (var rs = ventaStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idVenta = rs.getInt(1);
                }
            }

            // Insertar detalles de la venta
            String detalleSQL = "INSERT INTO DetalleVenta (idVenta, nombreProducto, cantProducto, tallaProducto, precioProducto) VALUES (?, ?, ?, ?, ?)";
            detalleStmt = con.prepareStatement(detalleSQL);
            for (DetalleVenta detalle : detalles) {
                detalleStmt.setInt(1, idVenta);
                detalleStmt.setString(2, detalle.getNombreProducto());
                detalleStmt.setInt(3, detalle.getCantProducto());
                detalleStmt.setString(4, detalle.getTallaProducto());
                detalleStmt.setBigDecimal(5, detalle.getPrecioProducto());
                detalleStmt.addBatch();
            }
            detalleStmt.executeBatch();

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Error al guardar los datos del carrito en la base de datos", e);
        } finally {
            if (ventaStmt != null) {
                try {
                    ventaStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (detalleStmt != null) {
                try {
                    detalleStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void sendEstadoActualizacionEmail(String email, String nombres, String nuevoEstado) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Actualización de Estado de Venta");
        message.setText("Hola " + nombres + ",\n\nTu estado de venta ha sido actualizado a: " + nuevoEstado);

        mailSender.send(message);
    }

}
