package com.mvc.web.Services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {
    @NotEmpty
    private String dni;

    @NotEmpty
    private String nombres;

    @NotEmpty
    private String apellidos;

    @NotEmpty
    private String numTelefono;

    @NotEmpty
    @Email
    private String correo;

    @NotEmpty
    private String rol;

    @NotEmpty
    private String contrasena;
}
