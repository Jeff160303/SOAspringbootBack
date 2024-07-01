package com.administradorservice.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @NotEmpty
    private int idProducto;

    @NotEmpty
    private String nombreProducto;

    @NotEmpty
    private String catProducto;

    @NotEmpty
    private Double precioProducto;

    @NotEmpty
    private int tallaS;

    @NotEmpty
    private int tallaM;

    @NotEmpty
    private int tallaL;

    private String imagenProducto;

}
