package com.administradorservice.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProducto {

    @NotEmpty
    private int idDetallePro;

    @NotEmpty
    private int idproducto;

    @NotEmpty
    private String tallas;

    @NotEmpty
    private int stock;

    private String imagenProducto;
}
