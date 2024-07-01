package com.mvc.web.Services;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdatePasswordDTO {
    @NotEmpty
    @Email
    private String correo;

    @NotEmpty
    private String contrasena;
}
