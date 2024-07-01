package com.mvc.web.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String dni;
    private String nombres;
    private String apellidos;
    private String numTelefono;
    private String correo;
    private String rol;
}
