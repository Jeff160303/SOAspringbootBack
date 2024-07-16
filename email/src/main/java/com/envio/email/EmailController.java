package com.envio.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/bienvenida")
    public void sendWelcomeEmail(@RequestParam String email, @RequestParam String name) {
        emailService.sendWelcomeMessage(email, name);
    }

    @PostMapping("/boleta")
    public void sendBoletaEmail(@RequestBody BoletaRequest request) {
        emailService.guardarDatosDelCarrito(request.getVenta(), request.getDetalles());
        emailService.sendBoleta(request.getEmail(), request.getVenta(), request.getDetalles());
    }

    @PostMapping("/actualizacion-estado")
    public void sendEstadoActualizacionEmail(@RequestBody EstadoActualizacionRequest request) {
        emailService.sendEstadoActualizacionEmail(request.getEmail(), request.getNombres(), request.getNuevoEstado());
    }

}