package com.mvc.web.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {

    @NotEmpty
    private int idCarrito;

    @NotEmpty
    private int idProducto;

    @NotEmpty
    private String dni;

    @NotEmpty
    private String talla;

    @NotEmpty
    private int cantidadProducto;

}
