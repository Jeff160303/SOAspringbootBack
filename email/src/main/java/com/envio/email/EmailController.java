package com.envio.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}